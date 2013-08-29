define(['render','jquery','common','ajax','dialog','dynamic'], function (render, $, common, ajax, dialog, dynamic) {

    return {
        url: function (config) {
            return 'ui/pipeline/{0}/branch/{1}/parameter'.format(config.pipelineId, config.branchId)
        },
        render: render.asTableTemplate(
            'branch-parameter-list'
        )
    }

})