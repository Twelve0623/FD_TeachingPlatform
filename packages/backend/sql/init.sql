CREATE TABLE `course_chapter` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                  `course_id` bigint(22) NOT NULL COMMENT '课程ID',
                                  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父节点id',
                                  `level` int(11) NOT NULL DEFAULT '0' COMMENT '等级 0',
                                  `chapter_name` varchar(50) NOT NULL DEFAULT '' COMMENT '章节标题 例：绪论',
                                  `chapter_desc` varchar(50) NOT NULL DEFAULT '' COMMENT '章节描述 例：第一章',
                                  `chapter_tag` int(11) NOT NULL DEFAULT '0' COMMENT '章节页面标记 是否页面内章节 0 否 1 是',
                                  `sort` int(2) NOT NULL DEFAULT '0' COMMENT '排序',
                                  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_level` (`course_id`,`level`) USING BTREE,
                                  KEY `idx_parent_id` (`parent_id`,`course_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程章节信息'

CREATE TABLE `course_chapter_detail` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                         `course_id` bigint(22) NOT NULL COMMENT '课程ID',
                                         `course_chapter_id` int(11) NOT NULL COMMENT '课程章节ID',
                                         `course_chapter_level_parent_id` int(11) NOT NULL COMMENT '课程章节父ID 最大等级（最大章节）',
                                         `detail_content` varchar(5000) NOT NULL DEFAULT '' COMMENT '章节详情信息',
                                         `detail_type` int(11) NOT NULL DEFAULT '0' COMMENT '章节详情类型 0 富文本 1 代码块 2 题库 3 动画',
                                         `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `uni_course_chapter_id` (`course_chapter_id`) USING BTREE,
                                         KEY `idx_course_id_course_chapter_level_parent_id` (`course_id`,`course_chapter_level_parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程章节详情信息'

CREATE TABLE `course_chapter_user` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                       `course_id` bigint(22) NOT NULL COMMENT '课程ID',
                                       `course_chapter_id` int(11) NOT NULL COMMENT '课程章节ID',
                                       `course_chapter_level_parent_id` int(11) NOT NULL COMMENT '课程章节父ID 最大等级（最大章节）',
                                       `student_id` bigint(22) NOT NULL COMMENT '学生ID',
                                       `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `uni_student_id_course_id` (`student_id`,`course_id`,`course_chapter_id`) USING BTREE,
                                       KEY `idx_course_chapter_level_parent_id` (`course_chapter_level_parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程章节用户记录'

CREATE TABLE `course_info` (
                               `id` bigint(22) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `course_name` varchar(50) NOT NULL DEFAULT '' COMMENT '课程名称',
                               `course_profile` varchar(500) NOT NULL DEFAULT '' COMMENT '课程简介',
                               `course_images` varchar(500) NOT NULL DEFAULT '' COMMENT '课程图片',
                               `course_type` int(11) NOT NULL DEFAULT '0' COMMENT '课程类型 0 自建课程 1 外链',
                               `course_ext` varchar(500) NOT NULL DEFAULT '' COMMENT '课程拓展字段（外链需要）',
                               `course_status` int(11) NOT NULL DEFAULT '0' COMMENT '是否开放 0 开放 1 未开放',
                               `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_course_name` (`course_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='课程信息表'

CREATE TABLE `course_user` (
                               `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                               `course_id` bigint(22) NOT NULL COMMENT '课程ID',
                               `student_id` bigint(22) NOT NULL COMMENT '学生ID',
                               `learning_duration` bigint(22) NOT NULL COMMENT '学习时长 /s',
                               `learning_count` bigint(22) NOT NULL COMMENT '学习次数',
                               `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `idx_student_id_course_id` (`student_id`,`course_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程用户记录'

CREATE TABLE `student_info` (
                                `id` bigint(22) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `student_name` varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
                                `student_no` varchar(20) NOT NULL DEFAULT '' COMMENT '学号',
                                `password` varchar(256) NOT NULL DEFAULT '' COMMENT '用户密码',
                                `nick_name` varchar(200) NOT NULL DEFAULT '' COMMENT '昵称',
                                `speciality_name` varchar(200) NOT NULL DEFAULT '' COMMENT '专业名称',
                                `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除 0：未删除 1 ：已删除',
                                `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0:正常;1:锁定',
                                `login_count` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
                                `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
                                `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `idx_student_no` (`student_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='学生信息表'
