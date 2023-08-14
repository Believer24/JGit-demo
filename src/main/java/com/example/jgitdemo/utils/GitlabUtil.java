package com.example.jgitdemo.utils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class GitlabUtil {
    public static String REP_ROOT_DIR = "C:\\Work\\JGit-demo\\.git";

    /**
     * 根据配置获得最新的git代码
     * @param repUrl
     * @param user
     * @param password
     * @throws Exception
     */
    public synchronized static void getLatestScripts(String repUrl, String user, String password) throws Exception {
        File file = new File(REP_ROOT_DIR);
        if(file.exists()) {
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository rep = builder.setGitDir(new File(REP_ROOT_DIR + "/.git")).readEnvironment()
                    .findGitDir()
                    .build();
            Git git = new Git(rep);
//            git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, password))
//                    .setRemote("origin").setRemoteBranchName("master").call();
        } else {
            Git.cloneRepository().setDirectory(file).setURI(repUrl)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, password)).call().close();
        }
    }

    /**
     * 上传指定文件更新到服务器
     * @param user
     * @param password
     * @throws Exception
     */
    public synchronized static void pushCommand(String user, String password, File file) throws Exception {
        if(file != null) {
            Git git = Git.open(new File(REP_ROOT_DIR ));
            String workTreePath = git.getRepository().getWorkTree().getCanonicalPath();
            String pagePath = file.getCanonicalPath();
            pagePath = pagePath.substring(workTreePath.length());
            pagePath = pagePath.replace(File.separatorChar, '\\');
            if (pagePath.startsWith("\\")) {
                pagePath = pagePath.substring(1);
            }

            git.add().addFilepattern(".").call();
            git.commit().setMessage( "后台自动上传备注" ).call();
            Iterable<PushResult> list = git.push().setRemote("https://github.com/Believer24/JGit-demo.git").add("main").setCredentialsProvider(new UsernamePasswordCredentialsProvider("Believer24", "Zh19930315")).call();
            if(list != null) {
                for(PushResult pr : list) {
                    System.out.println(pr.getMessages());
                }
            }
        }
    }
}
