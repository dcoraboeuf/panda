package net.panda.backend.security;

import net.panda.service.security.PipelineFunction;
import net.panda.service.security.PipelineGrant;
import net.panda.service.security.PipelineGrantId;

public class SampleImpl implements SampleAPI {

	@Override
	public void no_constraint() {
	}

	@Override
	@PipelineGrant(PipelineFunction.UPDATE)
	public void pipeline_call_missing_param(int pipeline) {
	}

	@Override
	@PipelineGrant(PipelineFunction.UPDATE)
	public void pipeline_call_too_much(@PipelineGrantId int pipeline, @PipelineGrantId int additional) {
	}

	@Override
	@PipelineGrant(PipelineFunction.UPDATE)
	public void pipeline_call_ok(@PipelineGrantId int pipeline, String name) {
	}
}
