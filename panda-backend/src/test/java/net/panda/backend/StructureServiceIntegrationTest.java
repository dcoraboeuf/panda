package net.panda.backend;

import net.panda.backend.security.AbstractSecurityTest;
import net.panda.core.model.PipelineCreationForm;
import net.panda.core.model.PipelineSummary;
import net.panda.service.StructureService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertNotNull;

public class StructureServiceIntegrationTest extends AbstractSecurityTest {

    @Autowired
    private StructureService service;

    @Test
    public void pipelineCreate() throws Exception {
        PipelineSummary p = asAdmin().call(new Callable<PipelineSummary>() {

            @Override
            public PipelineSummary call() throws Exception {
                return service.createPipeline(new PipelineCreationForm("SSIT1", "SSIT1 pipeline"));
            }
        });
        assertNotNull(p);
    }

}
