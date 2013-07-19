package org.jenkins.plugin.panda;

import hudson.Extension;
import hudson.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
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

    @Override
    protected void submit(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException, Descriptor.FormException {
        // Default
        super.submit(req, rsp);
        // Gets the JSON for the form
        JSONObject json = req.getSubmittedForm();
        // Main branch
        saveMainBranch(json);
        // Branches
        saveBranches(json);
    }

    private void saveBranches(JSONObject json) throws Descriptor.FormException {
        // Clears the initial list
        branches.clear();
        // Recreates the list
        JSONArray jBranches = json.getJSONArray("branches");
        Set<String> names = new HashSet<String>();
        for (int i = 0; i < jBranches.size(); i++) {
            JSONObject jBranch = jBranches.getJSONObject(i);
            PandaBranch branch = asBranch(jBranch);
            // Controls
            String name = branch.getName();
            if (StringUtils.isBlank(name)) {
                throw new Descriptor.FormException(Messages.PandaBranch_Config_NameRequired(), "branches.name");
            } else if ("default".equals(name)) {
                throw new Descriptor.FormException(Messages.PandaBranch_Config_DefaultNotAllowed(), "branches.name");
            } else if (names.contains(name)) {
                throw new Descriptor.FormException(Messages.PandaBranch_Config_NameAlreadyUsed(name), "branches.name");
            }
            // OK
            names.add(name);
            branches.add(branch);
        }
    }

    private void saveMainBranch(JSONObject json) {
        mainBranch = asBranch(json.getJSONObject("mainBranch"));
    }

    private PandaBranch asBranch(JSONObject branch) {
        return new PandaBranch(
                branch.getString("name"),
                branch.getString("description")
        );
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
