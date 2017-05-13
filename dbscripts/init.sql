-- 初始化caseonline 数据库 表

--coj_user_role
INSERT INTO coj_user_role (role_id, role_type, role_name, rolebak1, rolebak2, rolebak3) VALUES (1, 'admin', '管理员', '', '', '');
INSERT INTO coj_user_role (role_id, role_type, role_name, rolebak1, rolebak2, rolebak3) VALUES (2, 'user', '用户', '', '', '');

--coj_user
INSERT INTO coj_user (user_name, password, email, role_id,  userbak1, userbak2, userbak3) VALUES ( 'admin5', '26a91342190d515231d7238b0c5438e1', 'wxh054510@sina.com', 1, '', '', '');

--coj_usecase_format
INSERT INTO coj_usecase_format (format_text, formatbak1, formatbak2, formatbak3) VALUES ( 'JavaMapString', '', '', '');
INSERT INTO coj_usecase_format (format_text, formatbak1, formatbak2, formatbak3) VALUES ( 'PythonMapString', '', '', '');

--coj_testcase_rela_cate
INSERT INTO coj_testcase_rela_cate (testcase_id, category_id, relabak1, relabak2, relabak3) VALUES (0, 0, '', '', '');

--coj_testcase_category
INSERT INTO coj_testcase_category (category_type, category_name, pre_category_id, catebak1, catebak2, catebak3) VALUES ('math', '数学类', 0, '', '', '');
INSERT INTO coj_testcase_category (category_type, category_name, pre_category_id, catebak1, catebak2, catebak3) VALUES ('tool', '工具类', 0, '', '', '');
--coj_testcase
INSERT INTO coj_testcase (testcase_id, is_public, testcase_name, testcase_code, language_id, total_submission, total_user_submission, max_coverage, testcase__description, testcase_param_attr, casebak1, casebak2, casebak3) VALUES (0, '', '', '', 0, 0, 0, 0, '', '', '', '', '');

--coj_submission
INSERT INTO coj_submission (submission_id, testcase_id, user_id, language_id, submission_submit_time, usecase, usecase_amount, usecase_coverage, judge_result_flag, judge_log, submitbak1, submitbak2, submitbak3) VALUES (0, 0, 0, 0, '', '', 0, 0, '', '', '', '', '');

--coj_option
INSERT INTO coj_option (option_id, key_, value_) VALUES (0, '', '');

--coj_language
INSERT INTO coj_language (language_name, build_file, format_id, languagebak1, languagebak2, languagebak3) VALUES ('java', '', 1, '', '', '');

--coj_judge_result
INSERT INTO coj_judge_result ( judge_result_flag, judge_result_name) VALUES ( 'PD', 'Pending');
INSERT INTO coj_judge_result ( judge_result_flag, judge_result_name) VALUES ( 'CFE', 'Create File Error');
INSERT INTO coj_judge_result ( judge_result_flag, judge_result_name) VALUES ( 'AE', 'Ant Error');
INSERT INTO coj_judge_result ( judge_result_flag, judge_result_name) VALUES ( 'PHE', 'Parse Html Error');
INSERT INTO coj_judge_result ( judge_result_flag, judge_result_name) VALUES ( 'FJ', 'Finished Judger');

--coj_excellence_usecase
INSERT INTO coj_excellence_usecase (submission_id, testcase_id, usecase_coverage, usecase_amount, usecase, excebak1, excebak2, excebak3) VALUES (0, 0, 0, 0, '', '', '', '');
