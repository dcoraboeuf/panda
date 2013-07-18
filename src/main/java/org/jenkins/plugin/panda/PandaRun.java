package org.jenkins.plugin.panda;

import hudson.model.Run;

import java.io.File;
import java.io.IOException;

public class PandaRun extends Run<PandaProject, PandaRun> {

    public PandaRun(PandaProject project) throws IOException {
        super(project);
    }

    public PandaRun(PandaProject project, File buildDir) throws IOException {
        super(project, buildDir);
    }
}
