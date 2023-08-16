package com.example.jgitdemo.service.impl;

import com.example.jgitdemo.service.RepositoryProvider;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

import java.io.File;

public class RepositoryProviderCloneImpl implements RepositoryProvider {
    private String repoPath;
    private String clientPath;
    public RepositoryProviderCloneImpl(String repoPath, String clientPath) {
        this.repoPath = repoPath;
        this.clientPath = clientPath;
    }
    @Override
    public Repository get() throws Exception {
        File client = new File(clientPath);
        if (!client.exists()) {
            client.mkdir();
        }
        try (Git result = Git.cloneRepository()
                .setURI(repoPath)
                .setDirectory(client)
                .call()) {
            return result.getRepository();
        }
    }
}
