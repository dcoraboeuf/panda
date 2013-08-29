define(['render','jquery','common','ajax','dialog','dynamic'], function (render, $, common, ajax, dialog, dynamic) {

    function updateParameter(pipelineId, branchId, parameterId) {
        // Enters in edition mode
        $('#value-' + parameterId).addClass('hidden');
        $('#action-' + parameterId).addClass('hidden');
        $('#input-' + parameterId).removeClass('hidden');
        $('#apply-' + parameterId).removeClass('hidden');
        $('#cancel-' + parameterId).removeClass('hidden');
        // Clears the error
        $('#error-' + parameterId).hide();
        // Value to edit
        $('#input-' + parameterId).val($('#value-' + parameterId).text().trim());
        // Focus
        $('#input-' + parameterId).focus();
    }

    function cancelUpdateParameter(pipelineId, branchId, parameterId) {
        // Quits the edition mode
        $('#value-' + parameterId).removeClass('hidden');
        $('#action-' + parameterId).removeClass('hidden');
        $('#input-' + parameterId).addClass('hidden');
        $('#apply-' + parameterId).addClass('hidden');
        $('#cancel-' + parameterId).addClass('hidden');
        // Clears the error
        $('#error-' + parameterId).hide();
    }

    function applyUpdateParameter(pipelineId, branchId, parameterId) {
        var value = $('#input-' + parameterId).val().trim();
        ajax.put({
            url: 'ui/pipeline/{0}/branch/{1}/parameter/{2}'.format(pipelineId, branchId, parameterId),
            data: {
                value: value
            },
            loading: {
                el: $('#input-' + parameterId)
            },
            errorFn: ajax.simpleAjaxErrorFn(ajax.elementErrorMessageFn($('#error-' + parameterId))),
            successFn: function () {
                $('#value-' + parameterId).text(value);
                cancelUpdateParameter(pipelineId, branchId, parameterId);
            }
        })
    }

    return {
        url: function (config) {
            return 'ui/pipeline/{0}/branch/{1}/parameter'.format(config.pipelineId, config.branchId)
        },
        render: render.asTableTemplate(
            'branch-parameter-list',
            function (config) {
                $('.branch-parameter-update').each(function (index, action) {
                    var parameterId = $(action).attr('data-parameter-id');
                    $(action).click(function () {
                        updateParameter(config.pipelineId, config.branchId, parameterId)
                    })
                });
                $('.branch-parameter-cancel').each(function (index, button) {
                    var parameterId = $(button).attr('data-parameter-id');
                    $(button).click(function () {
                        cancelUpdateParameter(config.pipelineId, config.branchId, parameterId)
                    })
                });
                $('.branch-parameter-apply').each(function (index, button) {
                    var parameterId = $(button).attr('data-parameter-id');
                    $(button).click(function () {
                        applyUpdateParameter(config.pipelineId, config.branchId, parameterId)
                    })
                });
                $('.branch-parameter-input').each(function (index, input) {
                    var parameterId = $(input).attr('data-parameter-id');
                    $(input).keyup(function (e) {
                        if (e.keyCode == 27) {
                            cancelUpdateParameter(config.pipelineId, config.branchId, parameterId)
                        }
                        if (e.keyCode == 13) {
                            applyUpdateParameter(config.pipelineId, config.branchId, parameterId)
                        }
                    })
                });
            }
        )
    }

})