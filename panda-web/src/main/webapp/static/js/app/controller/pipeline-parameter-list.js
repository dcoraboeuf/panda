define(['render'], function (render) {

    return {
        url: function (config) {
            return 'ui/pipeline/{0}/parameter'.format(config.pipelineId)
        }
    }

})