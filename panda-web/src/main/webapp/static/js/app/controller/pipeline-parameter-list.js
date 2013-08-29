define(['render','jquery','common','ajax','dialog','dynamic'], function (render, $, common, ajax, dialog, dynamic) {

    function updateParameter (pipelineId, parameterId) {
        ajax.get({
            url: 'ui/pipeline/{0}/parameter/{1}'.format(pipelineId, parameterId),
            successFn: function (parameterResource) {
                dialog.show({
                    title: 'pipeline.parameter.update'.loc(),
                    templateId: 'pipeline-parameter-update',
                    initFn: function (config) {
                        config.form.find('#parameter-name').val(parameterResource.data.name),
                        config.form.find('#parameter-description').val(parameterResource.data.description),
                        config.form.find('#parameter-default-value').val(parameterResource.data.defaultValue),
                        config.form.find('#parameter-overriddable').val(parameterResource.data.overriddable)
                    },
                    submitFn: function (config) {
                        ajax.put({
                            url: 'ui/pipeline/{0}/parameter/{1}'.format(pipelineId, parameterId),
                            data: {
                                name: $('#parameter-name').val(),
                                description: $('#parameter-description').val(),
                                defaultValue: $('#parameter-default-value').val(),
                                overriddable: $('#parameter-overriddable').is(':checked')
                            },
                            successFn: function () {
                                config.closeFn();
                                dynamic.reloadSection('pipeline-parameter-list');
                            },
                            errorFn: ajax.simpleAjaxErrorFn(config.errorFn)
                        });
                    }
                });
            }
        })
    }

    function deleteParameter (pipelineId, parameterId) {
        ajax.get({
            url: 'ui/pipeline/{0}/parameter/{1}'.format(pipelineId, parameterId),
            successFn: function (parameterResource) {
                common.confirmAndCall(
                    'pipeline.parameter.delete'.loc(parameterResource.data.name),
                    function () {
                        ajax.del({
                            url: 'ui/pipeline/{0}/parameter/{1}'.format(pipelineId, parameterId),
                            successFn: function () {
                                dynamic.reloadSection('pipeline-parameter-list');
                            }
                        });
                    }
                )
            }
        })
    }

    return {
        url: function (config) {
            return 'ui/pipeline/{0}/parameter'.format(config.pipelineId)
        },
        render: render.asTableTemplate(
            'pipeline-parameter-list',
            function (config) {
                $('.parameter-update').each(function (index, action) {
                    $(action).click(function () {
                        updateParameter(config.pipelineId, $(action).attr('data-parameter-id'))
                    })
                });
                $('.parameter-delete').each(function (index, action) {
                    $(action).click(function () {
                        deleteParameter(config.pipelineId, $(action).attr('data-parameter-id'))
                    })
                });
            }
        )
    }

})