package net.panda.backend;

import net.panda.core.model.PipelineCreationForm;
import net.panda.core.model.PipelineSummary;
import net.panda.core.security.SecurityUtils;
import net.panda.service.StructureService;
import net.panda.test.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertNotNull;

public class StructureServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private StructureService service;

    @Autowired
    private SecurityUtils securityUtils;

    @Test
    public void pipelineCreate() {
        PipelineSummary p = securityUtils.asAdmin(new Callable<PipelineSummary>() {

            @Override
            public PipelineSummary call() throws Exception {
                return service.createPipeline(new PipelineCreationForm("SSIT1", "SSIT1 pipeline"));
            }
        });
        assertNotNull(p);
    }

}
