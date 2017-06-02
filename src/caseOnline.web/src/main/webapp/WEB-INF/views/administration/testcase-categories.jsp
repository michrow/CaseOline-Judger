<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')" var="cdnUrl" />
<!DOCTYPE html>
<html>
<head>
  
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/flat-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/problem-categories.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/flat-ui.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/pace.min.js"></script>
</head>
<body>
    <div id="wrapper">
        <!-- Sidebar -->
        <%@ include file="/WEB-INF/views/administration/include/sidebar.jsp" %>
        <div id="container">
            <!-- Header -->
            <%@ include file="/WEB-INF/views/administration/include/header.jsp" %>
            <!-- Content -->
            <div id="content">
                <h2 class="page-header"><i class="fa fa-th-list"></i>案例类型</h2>
                <div class="row-fluid">
                    <div class="span4">
                        <h4>添加分类</h4>
                        <form id="new-category-form" onSubmit="onSubmit(); return false;">
                            <div class="alert alert-error hide"></div> <!-- .alert-error -->
                            <div class="control-group row-fluid">
                                <label for="category-name">类型</label>
                                <input id="category-name" class="span12" type="text" maxlength="32" />
                            </div> <!-- .control-group -->
                            <div class="control-group row-fluid">
                                <label for="category-slug">名称</label>
                                <input id="category-slug" class="span12" type="text" maxlength="32" />
                            </div> <!-- .control-group -->
                           
                            <div class="row-fluid">
                                <button class="btn btn-primary" type="submit">添加</button>
                            </div> <!-- .row-fluid -->
                        </form> <!-- #new-category-form -->
                    </div> <!-- .span4 -->
                    <div class="span8">
                        <div id="filters" class="row-fluid">
                            <div class="span12">
                                <select id="actions">
                                    <option value="delete">删除</option>
                                </select>
                                <button class="btn btn-danger" id='delete'>删除</button>
                            </div> <!-- .span12 -->
                        </div> <!-- .row-fluid -->
                        <table id="problem-categories" class="table table-striped">
                            <thead>
                                <tr>
                                    <th class="check-box">
                                        <label class="checkbox all-problem-categories" for="all-problem-categories-thead">
                                            <input id="all-problem-categories-thead" type="checkbox" data-toggle="checkbox">
                                        </label>
                                    </th>
                                    <th>类型</th>
                                    <th>名称</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${categories}" var="cate">
                                <tr  data-value="${cate.categoryId}">
                                    <td class="check-box">
                                        <label class="checkbox" for="problem-category-${cate.categoryId}">
                                            <input id="problem-category-cate.categoryId}" type="checkbox" value="${cate.categoryId}" data-toggle="checkbox" />
                                        </label>
                                    </td>
                                    <td>
                                        <span class="problem-category-name">${cate.categoryType}</span>
                                       
                                    </td>
                                    <td><span class="problem-category-slug">${cate.categoryName}</span></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                           
                        </table>
                    </div> <!-- .span8 -->
                </div> <!-- .row-fluid -->
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %>
   
    <script type="text/javascript">
        $('label.all-problem-categories').click(function() {
            // Fix the bug for Checkbox in FlatUI 
            var isChecked = false,
                trigger   = $(this);
            setTimeout(function() {
                isChecked = $(trigger).hasClass('checked');
                
                if ( isChecked ) {
                    $('label.checkbox').addClass('checked');
                } else {
                    $('label.checkbox').removeClass('checked');
                }
            }, 100);
        });
    </script>
    <script type="text/javascript">
        $('#delete').click(function() {
        	if (!confirm('确定删除？')) {
				return;
			}
        	var categories = []

			$('label.checkbox', 'table tbody').each(
					function() {
						if ($(this).hasClass('checked')) {
							var testcaseId = $(
									'input[type=checkbox]',
									$(this)).val();
							categories.push(testcaseId);
						}
					});

          
            return doDeleteProblemCategoriesAction(categories);
        });
    </script>
    <script type="text/javascript">
        function doDeleteProblemCategoriesAction(categories) {
            var postData = {
                'categories': JSON.stringify(categories)
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/admin/testcase/deleteCategories.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    if ( result['isSuccessful'] ) {
                    	 for ( var i = 0; i < categories.length; ++ i ) {
                             $('tr[data-value=%s]'.format(categories[i]), '#problem-categories').remove();
                         }
                    }
                    $('button.btn-danger', '#filters').html('删除');
                    $('button.btn-danger', '#filters').removeAttr('disabled');
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processDeleteProblemCategoryResult(deletedProblemCategories) {
            for ( var i = 0; i < deletedProblemCategories.length; ++ i ) {
                var problemCategoryId = deletedProblemCategories[i];
                $('tr[data-value=%s]'.format(problemCategoryId), '#problem-categories').remove();
            }
        }
    </script>
    <script type="text/javascript">
        function onSubmit() {
            var categoryType     = $('#category-slug').val(),
            categoryName     = $('#category-name').val();

            $('.alert-error', '#new-category-form').addClass('hide');
            $('button[type=submit]', '#new-category-form').attr('disabled', 'disabled');
            $('button[type=submit]', '#new-category-form').html('等待...');

            return doCreateProblemCategoryAction(categoryType, categoryName);
        }
    </script>
    <script type="text/javascript">
        function doCreateProblemCategoryAction(categoryType, categoryName) {
            var postData = {
                'categoryType': categoryType,
                'categoryName': categoryName
            };

            $.ajax({
                type: 'POST',
                url: '<c:url value="/admin/testcase/createCategory.action" />',
                data: postData,
                dataType: 'JSON',
                success: function(result){
                    return processCreateProblemCategoryResult(result);
                }
            });
        }
    </script>
    <script type="text/javascript">
        function processCreateProblemCategoryResult(result) {
            if ( result['isSuccessful'] ) {
                window.location.reload();
            } else {
                var errorMessage  = '新增出错';

                $('.alert-error', '#new-category-form').html(errorMessage);
                $('.alert-error', '#new-category-form').removeClass('hide');
            }
            $('button[type=submit]', '#new-category-form').removeAttr('disabled');
            $('button[type=submit]', '#new-category-form').html('添加新的类别');
        }
    </script>
    <script type="text/javascript">
        function getParentProblemCategoriesOptions() {
            var options = '';

            for (var problemCategoryId in problemCategoriesOptions) {
                var problemCategory = problemCategoriesOptions[problemCategoryId];

                if ( problemCategoriesOptions.hasOwnProperty(problemCategoryId) ) {
                    options += '<option value="%s">%s</option>'.format(
                        problemCategory['problemCategorySlug'], 
                        problemCategory['problemCategoryName']
                    );
                }
            }
            return options;
        }
    </script>
</body>
</html>