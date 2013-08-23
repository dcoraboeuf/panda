package net.panda.backend.security;

import net.panda.core.model.PipelineCreationForm;
import net.panda.core.model.PipelineSummary;
import net.panda.service.StructureService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StructureServiceIntegrationSecurityTest extends AbstractSecurityTest {

	@Autowired
	private StructureService structureService;

	@Test
	public void pipeline_list_anonymous_ok() throws Exception {
		asAnonymous(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				structureService.getPipelines();
				return null;
			}
		});
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void pipeline_create_anonymous_denied() throws Exception {
		asAnonymous(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				structureService.createPipeline(new PipelineCreationForm("pipeline_anonymous",
						"Cannot create a pipeline"));
				return null;
			}
		});
	}

	@Test(expected = AccessDeniedException.class)
	public void pipeline_create_user_denied() throws Exception {
		asUser(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				structureService.createPipeline(new PipelineCreationForm("pipeline_user", "Cannot create a pipeline"));
				return null;
			}
		});
	}

	@Test
	public void pipeline_create_admin_ok() throws Exception {
		PipelineSummary pipeline = asAdmin(new Callable<PipelineSummary>() {
			@Override
			public PipelineSummary call() throws Exception {
				return structureService.createPipeline(new PipelineCreationForm("pipeline_admin", "OK"));
			}
		});
		assertNotNull(pipeline);
		assertEquals("pipeline_admin", pipeline.getName());
	}

}
