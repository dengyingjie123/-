/*
 * 说明：地调检修票
 * 程序：侯亮
 * 时间：2005-10-24
 */


CREATE TABLE WORKFLOW_DDJXP                  --玉溪地调停电检修票
{
ID                  NUMBER(20),              -- 编号(工作流相关)
PH                  VARCHAR2(20),            -- 票号

TXRID               NUMBER(10),              -- 填写人ID
TXSJ                DATE,                    -- 填写时间
SQRID               NUMBER(10),              -- 申请人ID
SLRID               NUMBER(10),              -- 受理人ID
SBID                VARCHAR2(2000),          -- 设备ID
SBFILEID            VARCHAR2(200),           -- 设备附件
AQCS                VARCHAR2(2000),          -- 安全措施
AQCSFILEID          VARCHAR2(200),           -- 安全措施附件
GZNR                VARCHAR2(2000),          -- 工作内容
GZNRFILEID          VARCHAR2(200),           -- 工作内容附件
TDFW                VARCHAR2(2000),          -- 停电范围
TDFWFILEID          VARCHAR2(200),           -- 停电范围附件
JHSJQ               DATE,                    -- 计划时间起
JHSJZ               DATE,                    -- 计划时间止
GGSJQ               DATE,                    -- 更改时间起
GGSJZ               DATE,                    -- 更改时间止
YQSJ                DATE,                    -- 延期时间
YQSQRID             NUMBER(10),              -- 延期人
YQYY                VARCHAR2(2000),          -- 延期原因
YQPZRID             NUMBER(10),              -- 延期批准人
YQYJ                VARCHAR2(200),           -- 延期意见
YQFILEID            NUMBER(10),              -- 延期附件
YQPZ                VARCHAR2(20),            -- 延期批准
LXFF                VARCHAR2(500),           -- 停复电联系方法



YXBSLRHPSQRJN       VARCHAR2(200),           -- 营销部受理人核批申请人技能
YXBSLRHPJDSX        VARCHAR2(200),           -- 营销部受理人核批交待事项
YXBSLRHRTZ          VARCHAR2(200),           -- 营销部受理人核批停电通知
YXBSLRHPSBCQ        VARCHAR2(200),           -- 营销部受理人核批停电设备产权归属
YXBSLRHPCZFS        VARCHAR2(200),           -- 营销部受理人核批停电操作方式
YXBSLRQTSX          VARCHAR2(200),           -- 营销部受理人核批其他事项
YXBSLRHPSJ          DATE,                    -- 营销部受理人核批时间
YXBSLRID            NUMBER(10),              -- 营销部受理人ID  
YXBLDFHYJ           VARCHAR2(2000),          -- 营销部领导复合意见
YXBLDFHYJ           VARCHAR2(200),           -- 营销部领导复合附件
YXBLDFHPZ           VARCHAR2(20),            -- 营销部领导复合批准
YXBLDFHSJ           DATE,                    -- 营销部领导复合时间


GSSJBSPYJ           VARCHAR2(2000),          -- 公司生技部审批意见
GSSJBSPFEID         VARCHAR2(200),           -- 公司生技部审批附件
GSSJBSPPZ           VARCHAR2(20),            -- 公司生技部审批批准
GSSJBSPSJ           DATE,                    -- 公司生技部审批时间
GSZGSPYJ            VARCHAR2(2000),          -- 公司主管审批意见
GSZGSPFIEID         VARCHAR2(200),           -- 公司主管审批附件
GSZGSPPZ            VARCHAR2(20),            -- 公司主管审批批准
GSZHSPSJ            DATE,                    -- 公司主管审批时间
XDPFYJ              VARCHAR2(2000),          -- 县调批复意见
XDPFYJ              VARCHAR2(200),           -- 县调批复附件
XDPFPZ              VARCHAR2(20),            -- 县调批复批准
XDPFYJ              DATE,                    -- 县调批复时间
FSPFYJ              VARCHAR2(2000),          -- 方式批复意见
FSPFFILEID          VARCHAR2(200),           -- 方式批复附件
FSPFYID             NUMBER(10),              -- 方式员ID
FSPFSJ              DATE,                    -- 方式批复时间
FSPFPZ              VARCHAR2(20),            -- 方式批复批准
FSPFJH              NUMBER(10),              -- 方式批复校核
FSPFJHSJ            DATE,                    -- 方式批复校核时间
JDPFYJ              VARCHAR2(2000),          -- 继电批复意见
JDPFFILEID          VARCHAR2(200),           -- 继电批复附件
JDPFYID             NUMBER(10),              -- 继电员ID
JDPFPZ              VARCHAR2(20),            -- 继电批复批准
JDPFJHID            NUMBER(10),              -- 继电批复校核
DDSYJ               VARCHAR2(2000),          -- 调度所意见
DDSFILEID           VARCHAR2(200),           -- 调度所附件
DDSPZ               VARCHAR2(20),            -- 调度所批准
DDSSJ               DATE,                    -- 调度所时间
DDZYJ               VARCHAR2(2000),          -- 调度组意见
DDZFILEID           VARCHAR2(200),           -- 调度组附件
DDZPZ               VARCHAR2(20),            -- 调度组批准
DDZSJ               DATE,                    -- 调度组时间
DDYYJ               VARCHAR2(2000),          -- 调度员意见
DDYFILEID           VARCHAR2(200),           -- 调度员附件
DDYPZ               VARCHAR2(20),            -- 调度员批准
DDYSJ               DATE,                    -- 调度员时间

YGSJ                DATE,                    -- 预告时间
XLSJ                DATE,                    -- 下令时间
TDSJ                DATE,                    -- 停电时间
KGSJ                DATE,                    -- 开工时间
WGSJ                DATE,                    -- 完工时间
FDSJ                DATE,                    -- 复电时间
YGFZRID             NUMBER(10),              -- 预告对方负责人
XLFZRID             NUMBER(10),              -- 下令对方负责人
TDFZRID             NUMBER(10),              -- 停电对方负责人
KGFZRID             NUMBER(10),              -- 开工对方负责人
WGFZRID             NUMBER(10),              -- 完工对方负责人
FDFZRID             NUMBER(10),              -- 复电对方负责人
YGDDYID             NUMBER(10),              -- 预告当值调度员
XLDDYID             NUMBER(10),              -- 下令当值调度员
TDDDYID             NUMBER(10),              -- 停电当值调度员
KGDDYID             NUMBER(10),              -- 开工当值调度员
WGDDYID             NUMBER(10),              -- 完工当值调度员
FDDDYID             NUMBER(10),              -- 复电当值调度员
BZ                  VARCHAR2(4000),          -- 备注
BZFILEID            VARCHAR2(200),           -- 备注附件
ZFYY                VARCHAR2(2000),          -- 作废原因
ZFFILEID            VARCHAR2(200),           -- 作废附件
ZFRID               NUMBER(10),              -- 作废人ID
ZFSJ                DATE,                    -- 作废时间
WCBZ                VARCHAR2(20),            -- 完成标志
WCBZ                VARCHAR2(20),            -- 完成标志 0:未完成 1：已完成 2：已作废
LSBZ                VARCHAR2(20),            -- 历史标志 0:未转历史 1:转历史
EXT1                VARCHAR2(400),           -- 备用字段1 
EXT2                VARCHAR2(400),           -- 备用字段2 
EXT3                VARCHAR2(400),           -- 备用字段3 
EXT4                VARCHAR2(400),           -- 备用字段4 
EXT5                VARCHAR2(400),           -- 备用字段5 
EXT6                VARCHAR2(400),           -- 备用字段6 
EXT7                VARCHAR2(400),           -- 备用字段7 
EXT8                VARCHAR2(400),           -- 备用字段8 
EXT9                VARCHAR2(400),           -- 备用字段9 
EXT10               VARCHAR2(400),           -- 备用字段10
EXT11               VARCHAR2(400),           -- 备用字段11
EXT12               VARCHAR2(400),           -- 备用字段12
EXT13               VARCHAR2(400),           -- 备用字段13
EXT14               VARCHAR2(400),           -- 备用字段14
EXT15               VARCHAR2(400),           -- 备用字段15
EXT16               VARCHAR2(400),           -- 备用字段16
EXT17               VARCHAR2(400),           -- 备用字段17
EXT18               VARCHAR2(400),           -- 备用字段18
EXT19               VARCHAR2(400),           -- 备用字段19
EXT20               VARCHAR2(400),           -- 备用字段20
EXT22               VARCHAR2(400),           -- 备用字段21
EXT22               VARCHAR2(400),           -- 备用字段22
EXT23               VARCHAR2(400),           -- 备用字段23
EXT24               VARCHAR2(400),           -- 备用字段24
EXT25               VARCHAR2(400),           -- 备用字段25
EXT26               VARCHAR2(400),           -- 备用字段26
EXT27               VARCHAR2(400),           -- 备用字段27
EXT28               VARCHAR2(400),           -- 备用字段28
EXT29               VARCHAR2(400),           -- 备用字段29
EXT30               VARCHAR2(400)            -- 备用字段30

}