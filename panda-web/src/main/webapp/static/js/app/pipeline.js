define(['dialog', 'jquery', 'ajax', 'dynamic', 'dialog'], function(dialog, $, ajax, dynamic, dialog) {

    var pipelineId = $('#pipeline-id').val();

    function pipelineRun () {
        ajax.get({
            url: 'ui/pipeline/{0}/branch'.format(pipelineId),
            successFn: function (branches) {
                ajax.get({
                    url: 'ui/pipeline/{0}/parameter'.format(pipelineId),
                    successFn: function (parameters) {
                        dialog.show({
                            title: 'pipeline.run'.loc(),
                            templateId: 'pipeline-run',
                            data: {
                                branches: branches,
                                parameters: parameters
                            },
                            initFn: function (dialog) {
                                dialog.get('#pipeline-branch').change(function () {
                                    var branchId = dialog.get('#pipeline-branch').val();
                                    ajax.get({
                                        url: 'ui/pipeline/{0}/branch/{1}/parameter'.format(pipelineId, branchId),
                                        successFn: function (branchParameters) {
                                            $.each(branchParameters, function (index, branchParameter) {
                                                if (branchParameter.data.def.overriddable) {
                                                    dialog.get('#run-parameter-' + branchParameter.data.def.id).val(branchParameter.data.actualValue)
                                                } else {
                                                    dialog.get('#run-parameter-' + branchParameter.data.def.id).text(branchParameter.data.actualValue)
                                                }
                                            })
                                        }
                                    })
                                })
                            }
                        })
                    }
                })
            }
        })
    }

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

    function pipelineBranchCreate () {
        dialog.show({
            title: 'pipeline.branch.create'.loc(),
            templateId: 'pipeline-branch-create',
            submitFn: function (config) {
                ajax.post({
                    url: 'ui/pipeline/{0}/branch'.format(pipelineId),
                    data: {
                        name: $('#branch-name').val(),
                        description: $('#branch-description').val()
                    },
                    successFn: function (branch) {
                        config.closeFn();
                        'pipeline/{0}/branch/{1}'.format(pipelineId, branch.data.id).goto();
                    },
                    errorFn: ajax.simpleAjaxErrorFn(config.errorFn)
                });
            }
        });
    }

    function pipelineAuthorizationCreate () {
        ajax.get({
            url: 'ui/account/user',
            successFn: function (accounts) {
                dialog.show({
                    title: 'pipeline.authorization.create'.loc(),
                    templateId: 'pipeline-authorization-create',
                    data: {
                        accounts: accounts
                    },
                    submitFn: function (config) {
                        config.closeFn();
                        ajax.put({
                            url: 'ui/pipeline/{0}/authorization/{1}/{2}'.format(
                                pipelineId,
                                config.form.find('#authorization-user').val(),
                                config.form.find('#authorization-role').val()
                            ),
                            successFn: function () {
                                dynamic.reloadSection('pipeline-authorization-list');
                            }
                        })
                    }
                });
            }
        })
    }

    $('#pipeline-parameter-create').click(pipelineParameterCreate);
    $('#pipeline-branch-create').click(pipelineBranchCreate);
    $('#pipeline-authorization-create').click(pipelineAuthorizationCreate);
    $('#pipeline-run').click(pipelineRun);

})