define(['dialog', 'jquery', 'ajax', 'dynamic'], function(dialog, $, ajax, dynamic) {

    var pipelineId = $('#pipeline-id').val();

    function pipelineParameterCreate () {
        dialog.show({
            title: 'pipeline.parameter.create'.loc(),
            templateId: 'pipeline-parameter-create',
            submitFn: function (config) {
                ajax.post({
                    url: 'ui/pipeline/{0}/parameter'.format(pipelineId),
                    data: {
                        name: $('#parameter-name').val(),
                        description: $('#parameter-description').val(),
                        defaultValue: $('#parameter-default-value').val(),
                        overriddable: $('#parameter-overriddable').is(':checked')
                    },
                    successFn: function (pipeline) {
                        config.closeFn();
                        dynamic.reloadSection('pipeline-parameter-list');
                    },
                    errorFn: ajax.simpleAjaxErrorFn(config.errorFn)
                });
            }
        });
    }

    $('#pipeline-parameter-create').click(pipelineParameterCreate);

})