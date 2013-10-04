package br.com.caelum.zhit.model;

import static br.com.caelum.zhit.infra.ZhitFileUtils.readFileToString;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.com.caelum.zhit.infra.GitBlobInflater;
import br.com.caelum.zhit.infra.GitCommitInflater;
import br.com.caelum.zhit.infra.GitTreeInflater;
import br.com.caelum.zhit.infra.ZhitFileUtils;
import br.com.caelum.zhit.model.internal.GitObject;
import br.com.caelum.zhit.model.internal.PackedRefs;
import br.com.caelum.zhit.model.internal.Sha1;
import br.com.caelum.zhit.parser.GitBlobParser;
import br.com.caelum.zhit.parser.GitCommitParser;
import br.com.caelum.zhit.parser.GitTreeParser;

public class GitRepository {

	private final File root;
	private final File dotGit;
	private final boolean bare;
	private final PackedRefs packedRefs;

	public static GitRepository local(File repositoryRoot) {
		return new GitRepository(repositoryRoot, false);
	}
	
	public static GitRepository bare(File repositoryRoot) {
		return new GitRepository(repositoryRoot, true);
	}
	
	private GitRepository(File repositoryRoot, boolean bare) {
		this.bare = bare;
		this.root = repositoryRoot;
		this.dotGit = bare ? root : new File(root, ".git");
		this.packedRefs = new PackedRefs(dotGit);
	}

	public GitCommit head() {
		File head = new File(dotGit, "HEAD");
		String headContent = ZhitFileUtils.readFileToString(head);
		String headBranch = headContent.split(":")[1].trim();
		Sha1 headHash = extractHeadHash(headBranch);
		
		GitObject<GitCommit> gitObject = new GitObject<GitCommit>(headHash, this, new GitCommitInflater());
		GitCommit commit = gitObject.extract(new GitCommitParser(this));
		return commit;
	}

	private Sha1 extractHeadHash(final String headBranch) {
		File headBranchFile = new File(dotGit, headBranch);
		if (headBranchFile.exists()) {
			return new Sha1(ZhitFileUtils.readFileToString(headBranchFile));
		}
		
		return packedRefs.sha1(headBranch);
	}
	
	public File path() {
		return root.getAbsoluteFile();
	}

	public boolean isBare() {
		return bare;
	}

	public File dotGit() {
		return dotGit;
	}

	public GitTree parseTree(Sha1 sha1) {
		return new GitObject<GitTree>(sha1, this, new GitTreeInflater()).extract(new GitTreeParser());
	}

	public GitBlob parseBlob(Sha1 sha1) {
		return new GitObject<GitBlob>(sha1, this, new GitBlobInflater()).extract(new GitBlobParser());
	}

	public List<GitBranch> branches() {
		Set<GitBranch> branches = new TreeSet<GitBranch>(branchComparator());
		
		File[] listFiles = new File(dotGit, "refs/heads").listFiles();
		for (File file : listFiles) {
			Sha1 sha1 = new Sha1(readFileToString(file).trim());
			branches.add(new GitBranch(file.getName(), sha1));
		}
		
		branches.addAll(packedRefs.locals());
		return new ArrayList<>(branches);
	}

	public Collection<GitBranch> remoteBranches() {
		File[] remotesDir = new File(dotGit, "refs/remotes").listFiles();
		Collection<GitBranch> branches = new ArrayList<>();
		for (File remote : remotesDir) {
			File[] remoteBranches = remote.listFiles();
			for (File branch : remoteBranches) {
				String name = remote.getName() + "/" + branch.getName();
				String sha = ZhitFileUtils.readFileToString(branch).trim();
				branches.add(new GitBranch(name, new Sha1(sha)));
			}
		}
		
		branches.addAll(packedRefs.remotes());
		return branches;
	}
	
	private Comparator<GitBranch> branchComparator() {
		return new Comparator<GitBranch>() {
			@Override
			public int compare(GitBranch b1, GitBranch b2) {
				if (b1.name().equals(b2.name()))
					return 0;
				return 1;
			}
		};
	}

	public GitHistory history() {
		return new GitHistory(head());
	}
	
}
