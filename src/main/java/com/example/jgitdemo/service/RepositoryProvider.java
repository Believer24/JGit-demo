package com.example.jgitdemo.service;

import org.eclipse.jgit.lib.Repository;

public interface RepositoryProvider {
    Repository get() throws Exception;
}
