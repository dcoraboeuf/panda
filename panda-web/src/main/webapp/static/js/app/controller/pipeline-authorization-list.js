define(['render'], function (render) {

    return {
        url: function (config) {
            return 'ui/pipeline/{0}/authorization'.format(config.pipelineId)
        },
        render:render.asTableTemplate('pipeline-authorization-list')
    }

})