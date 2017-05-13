
function getRootPath(){ 
var strFullPath=window.document.location.href; 
var strPath=window.document.location.pathname; 
var pos=strFullPath.indexOf(strPath); 
var prePath=strFullPath.substring(0,pos); 
var postPath=strPath.substring(0,strPath.substring(1).indexOf('/')+1); 
return(prePath+postPath); 
} 
        function onSubmit() {
            $('.alert-error').addClass('hide');
            $('button[type=submit]').attr('disabled', 'disabled');
            $('button[type=submit]').html('<spring:message code="voj.accounts.register.please-wait" text="Please wait..." />');
            
            var username            = $('#username').val(),
                password            = $('#password').val(),
                email               = $('#email').val()
            return doRegisterAction(username, password, email);
        };

        function doRegisterAction(username, password, email) {
            var postData = {
                'username': username,
                'password': password,
                'email': email        
            };
            
            $.ajax({
                type: 'POST',
                url: getRootPath()+'/accounts/register.action',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processRegisterResult(result);
                }
            });
        }

        function processRegisterResult(result) {
            if ( result['isSuccessful'] ) {
                var forwardUrl = '${forwardUrl}' || getRootPath()+'/';
                window.location.href = forwardUrl;
            } else {
                var errorMessage  = '';

                if ( !result['isCsrfTokenValid'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.invalid-token" text="Invalid token." />';
                }
                if ( result['isUsernameEmpty'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.username-empty" text="You can&apos;t leave Username empty." /><br>';
                } else if ( !result['isUsernameLegal'] ) {
                    var username = $('#username').val();

                    if ( username.length < 6 || username.length > 16 ) {
                        errorMessage += '<spring:message code="voj.accounts.register.username-length-illegal" text="The length of Username must between 6 and 16 characters." /><br>';
                    } else if ( !username[0].match(/[a-z]/i) ) {
                        errorMessage += '<spring:message code="voj.accounts.register.username-beginning-illegal" text="Username must start with a letter(a-z)." /><br>';
                    } else {
                        errorMessage += '<spring:message code="voj.accounts.register.username-character-illegal" text="Username can only contain letters(a-z), numbers, and underlines(_)." /><br>';
                    }
                } else if ( result['isUsernameExists'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.username-existing" text="Someone already has that username." /><br>';
                }
                if ( result['isPasswordEmpty'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.password-empty" text="You can&apos;t leave Password empty." /><br>';
                } else if ( !result['isPasswordLegal'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.password-illegal" text="The length of Password must between 6 and 16 characters." /><br>';
                }
                if ( result['isEmailEmpty'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.email-empty" text="You can&apos;t leave Email empty." /><br>';
                } else if ( !result['isEmailLegal'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.email-illegal" text="The Email seems invalid." /><br>';
                } else if ( result['isEmailExists'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.email-existing" text="Someone already use that email." /><br>';
                }
                if ( !result['isLanguageLegal'] ) {
                    errorMessage += '<spring:message code="voj.accounts.register.language-empty" text="You can&apos;t leave Language Preference empty." /><br>';
                }

                $('.alert-error').html(errorMessage);
                $('.alert-error').removeClass('hide');
            }

            $('button[type=submit]').html('<spring:message code="voj.accounts.register.create-account" text="Create Account" />');
            $('button[type=submit]').removeAttr('disabled');
            $('html, body').animate({ scrollTop: 0 }, 100);
        }