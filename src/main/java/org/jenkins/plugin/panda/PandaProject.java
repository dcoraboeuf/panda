package org.jenkins.plugin.panda;

import hudson.Extension;
import hudson.model.ItemGroup;
import hudson.model.Job;
import hudson.model.TopLevelItem;
import hudson.model.TopLevelItemDescriptor;

import java.util.SortedMap;
import java.util.TreeMap;

public class PandaProject extends Job<PandaProject, PandaRun> implements TopLevelItem {

    @Extension
    public static final PandaProjectDescriptor DESCRIPTOR = new PandaProjectDescriptor();

    public PandaProject(ItemGroup parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean isBuildable() {
        return true;
    }

    @Override
    protected SortedMap<Integer, ? extends PandaRun> _getRuns() {
        // FIXME Gets the list of runs
        return new TreeMap<Integer, PandaRun>();
    }

    @Override
    protected void removeRun(PandaRun run) {
        // FIXME Deletes a run
    }

    public PandaProjectDescriptor getDescriptor() {
        return DESCRIPTOR;
    }

    public static class PandaProjectDescriptor extends TopLevelItemDescriptor {

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
