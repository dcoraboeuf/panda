package org.jenkins.plugin.panda;

import hudson.Extension;
import hudson.model.ItemGroup;
import hudson.model.Job;
import hudson.model.TopLevelItem;
import hudson.model.TopLevelItemDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.*;

public class PandaProject extends Job<PandaProject, PandaRun> implements TopLevelItem {

    /**
     * Extension as a type of job.
     */
    @Extension
    public static final PandaProjectDescriptor DESCRIPTOR = new PandaProjectDescriptor();
    /**
     * Main branch
     */
    private PandaBranch mainBranch;
    /**
     * List of branches
     */
    private List<PandaBranch> branches;

    @DataBoundConstructor
    public PandaProject(ItemGroup parent, String name, PandaBranch mainBranch, List<PandaBranch> branches) {
        super(parent, name);
        this.mainBranch = mainBranch;
        this.branches = new ArrayList<PandaBranch>(branches);
    }

    public PandaBranch getMainBranch() {
        return mainBranch;
    }

    public List<PandaBranch> getBranches() {
        return branches;
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
            return new PandaProject(parent, name, new PandaBranch("default", ""), Collections.<PandaBranch>emptyList());
        }
    }

}
