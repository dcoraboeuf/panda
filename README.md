panda
=====

In Jenkins, one deals with jobs, their parameters, their orchestration, their duplication between branches (using templating or not), the duplication of their orchestration, etc. In the Jenkins core, not much of this is available. One would need to install plug-ins, update ones or even create some from scratch. And then make them work together.

My feeling is that the idea of a pipeline should be central to the continuous integration.

The idea behind `panda` (1) is to be able to support pipelines.

One would define a `pipeline` in Panda with a given set of parameters. It would be associated with `stages` that are run in sequence, automatically, manually or conditionally (using triggers).

Stages are groups of jobs that are either run in parallel or in sequence. Each job accepts a subset of the parameters defined by the pipeline. A job can be linked to a real (remote) Jenkins job, or be anything else.

A pipeline can be defined with a default set of parameters, but `branches` can be defined as other sets of parameters.

One can run a pipeline for a given branch or the default branch. The input parameters can be overridden if needed. The outcome of such a run is designed as a `(pipeline) instance`. This `instance` will go through each stage, running all the associated jobs.

A dashboard per pipeline and branch allows to see at one glance the status of the last instances. From there, one can triggers the next manual stages if available.

---
(1) Probably a temporary name for this project... The idea came after a discussion - maybe an explanation could come later.
