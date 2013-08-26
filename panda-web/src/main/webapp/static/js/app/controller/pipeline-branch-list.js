define(['render','jquery','common','ajax','dialog','dynamic'], function (render, $, common, ajax, dialog, dynamic) {

    return {
        url: function (config) {
            return 'ui/pipeline/{0}/branch'.format(config.pipelineId)
        },
        render: render.asTableTemplate(
            'pipeline-branch-list',
            function (config, parameters) {
            }
        )
    }

})