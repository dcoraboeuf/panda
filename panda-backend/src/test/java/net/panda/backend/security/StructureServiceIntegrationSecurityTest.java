package net.panda.backend.security;

import net.panda.core.model.ParameterCreationForm;
import net.panda.core.model.ParameterSummary;
import net.panda.core.model.PipelineCreationForm;
import net.panda.core.model.PipelineSummary;
import net.panda.service.StructureService;
import net.panda.service.security.PipelineFunction;
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
		asAnonymous().call(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				structureService.getPipelines();
				return null;
			}
		});
	}

	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void pipeline_create_anonymous_denied() throws Exception {
		asAnonymous().call(new Callable<Void>() {
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
		asUser().call(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				structureService.createPipeline(new PipelineCreationForm("pipeline_user", "Cannot create a pipeline"));
				return null;
			}
		});
	}

	@Test
	public void pipeline_create_admin_ok() throws Exception {
		PipelineSummary pipeline = createPipeline("pipeline_admin");
		assertNotNull(pipeline);
		assertEquals("pipeline_admin", pipeline.getName());
	}

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void pipeline_parameter_create_anonymous_denied() throws Exception {
        final PipelineSummary pipeline = createPipeline("pipeline_parameter_create_anonymous");
        asAnonymous().call(new Callable<ParameterSummary>() {
            @Override
            public ParameterSummary call() throws Exception {
                return structureService.createParameter(pipeline.getId(), new ParameterCreationForm("git_remote",
                        "URL for the Git repository", "", false));
            }
        });
    }

    @Test(expected = AccessDeniedException.class)
    public void pipeline_parameter_create_user_denied() throws Exception {
        final PipelineSummary pipeline = createPipeline("pipeline_parameter_create_user");
        asUser().call(new Callable<ParameterSummary>() {
            @Override
            public ParameterSummary call() throws Exception {
                return structureService.createParameter(pipeline.getId(), new ParameterCreationForm("git_remote",
                        "URL for the Git repository", "", false));
            }
        });
    }

    @Test
    public void pipeline_parameter_create_grant_ok() throws Exception {
        final PipelineSummary pipeline = createPipeline("pipeline_parameter_create_grant");
        asUser().withPipelineGrant(pipeline.getId(), PipelineFunction.UPDATE).call(new Callable<ParameterSummary>() {
            @Override
            public ParameterSummary call() throws Exception {
                return structureService.createParameter(pipeline.getId(), new ParameterCreationForm("git_remote",
                        "URL for the Git repository", "", false));
            }
        });
    }

    @Test
    public void pipeline_parameter_create_admin_ok() throws Exception {
        final PipelineSummary pipeline = createPipeline("pipeline_parameter_create_admin");
        asAdmin().call(new Callable<ParameterSummary>() {
            @Override
            public ParameterSummary call() throws Exception {
                return structureService.createParameter(pipeline.getId(), new ParameterCreationForm("git_remote",
                        "URL for the Git repository", "", false));
            }
        });
    }

	private PipelineSummary createPipeline(final String name) throws Exception {
		return asAdmin().call(new Callable<PipelineSummary>() {
			@Override
			public PipelineSummary call() throws Exception {
				return structureService.createPipeline(new PipelineCreationForm(name, "OK"));
			}
		});
	}

}
