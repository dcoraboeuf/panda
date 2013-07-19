package org.jenkins.plugin.panda;

import hudson.model.Describable;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

public class PandaBranch implements Describable<PandaBranch> {

    private static final PandaBranchDescriptor DESCRIPTOR = new PandaBranchDescriptor();
    private final String name;
    private final String description;

    @DataBoundConstructor
    public PandaBranch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Descriptor<PandaBranch> getDescriptor() {
        return DESCRIPTOR;
    }

    public static class PandaBranchDescriptor extends Descriptor<PandaBranch> {

        @Override
        public String getDisplayName() {
            return "Branch";
        }
    }

}
