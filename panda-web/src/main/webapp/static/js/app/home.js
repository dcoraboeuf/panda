define(['dialog', 'jquery', 'ajax', 'common'], function(dialog, $, ajax, common) {

    /**
     * Creating a pipeline
     */
    function createPipeline() {
        dialog.show({
            title: 'pipeline.create'.loc(),
            templateId: 'pipeline-create',
            submitFn: function (config) {
                ajax.post({
                    url: 'ui/pipeline',
                    data: {
                        name: $('#pipeline-name').val(),
                        description: $('#pipeline-description').val()
                    },
                    successFn: function (pipeline) {
                        config.closeFn();
                        common.link(pipeline, 'gui').goto();
                    },
                    errorFn: ajax.simpleAjaxErrorFn(config.errorFn)
                });
            }
        });
    }

    //

    $('#pipeline-create').click(createPipeline);

});