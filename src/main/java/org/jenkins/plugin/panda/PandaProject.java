package org.jenkins.plugin.panda;

import hudson.Extension;
import hudson.model.ItemGroup;
import hudson.model.Project;
import hudson.model.TopLevelItem;

public class PandaProject extends Project<PandaProject, PandaRun> implements TopLevelItem {

    @Extension
    public static final PandaProjectDescriptor DESCRIPTOR = new PandaProjectDescriptor();

    public PandaProject(ItemGroup parent, String name) {
        super(parent, name);
    }

    @Override
    protected Class<PandaRun> getBuildClass() {
        return PandaRun.class;
    }

    public PandaProjectDescriptor getDescriptor() {
        return DESCRIPTOR;
    }

    public static class PandaProjectDescriptor extends AbstractProjectDescriptor {

        @Override
        public String getDisplayName() {
            return Messages.PandaProject();
        }

        @Override
        public TopLevelItem newInstance(ItemGroup parent, String name) {
            return new PandaProject(parent, name);
        }
    }

}
