    
        function onSubmit() {
            $('.alert-success').addClass('hide');
            $('.alert-error').addClass('hide');
            $('button[type=submit]').attr('disabled', 'disabled');
            $('button[type=submit]').html('<spring:message code="voj.accounts.login.please-wait" text="Please wait..." />');
            
            var username   = $('#username').val(),
                password   = md5($('#password').val()),
                rememberMe = $('input#remember-me').is(':checked');
            
            $('#password').val(password);
            return doLoginAction(username, password, rememberMe);
        };

        function doLoginAction(username, password, rememberMe) {
            var postData = {
                'username': username,
                'password': password,
                'rememberMe': rememberMe
            };
            
            $.ajax({
                type: 'POST',
                url: "/accounts/login.action",
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processLoginResult(result);
                }
            });
        }

        function processLoginResult(result) {
            if ( result['isSuccessful'] ) {
                var forwardUrl = '${forwardUrl}' || '<c:url value="/" />';
                window.location.href = forwardUrl;
            } else {
                var errorMessage = '';
                if ( !result['isAccountValid'] ) {
                    errorMessage = '<spring:message code="voj.accounts.login.incorrect-password" text="Incorrect username or password." />';
                } else if ( !result['isAllowedToAccess'] ) {
                    errorMessage = '<spring:message code="voj.accounts.login.forbidden-user" text="You&acute;re not allowed to sign in." />';
                }
                $('#password').val('');
                $('.alert-error').html(errorMessage);
                $('.alert-error').removeClass('hide');
            }

            $('button[type=submit]').html('<spring:message code="voj.accounts.login.sign-in" text="Sign in" />');
            $('button[type=submit]').removeAttr('disabled');
        }
