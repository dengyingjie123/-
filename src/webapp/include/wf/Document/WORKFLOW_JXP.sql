/*
 * ˵��������Ʊ
 * ��������
 * ʱ�䣺2005-10-24
 * �汾��1.1
 */
CREATE TABLE WORKFLOW_DATA_JXP                    -- ����Ʊ    ����
(
ID                  NUMBER(20),              -- ���(���������)
TXRID               NUMBER(10),              -- ��д��ID
TXSJ                DATE,                    -- ��дʱ��
PH                  VARCHAR2(20),            -- Ʊ��
TYPE                NUMBER(2),               -- 0:�������� 1����ʱ��������
SQRID               NUMBER(10),              -- ������ID
SQSJ                DATE,                    -- ����ʱ��
SLRID               NUMBER(10),              -- ������ID
SBID                VARCHAR2(2000),          -- �豸ID
SBFILEID            VARCHAR2(200),           -- �豸����
AQCS                VARCHAR2(2000),          -- ��ȫ��ʩ
AQCSFILEID          VARCHAR2(200),           -- ��ȫ��ʩ����
GZNR                VARCHAR2(2000),          -- ��������
GZNRFILEID          VARCHAR2(200),           -- �������ݸ���
TDFW                VARCHAR2(2000),          -- ͣ�緶Χ
TDFWFILEID          VARCHAR2(200),           -- ͣ�緶Χ����
JHSJQ               DATE,                    -- �ƻ�ʱ����
JHSJZ               DATE,                    -- �ƻ�ʱ��ֹ
SQDWPZ              VARCHAR2(20),            -- ���뵥λ��׼
LXFF                VARCHAR2(500),           -- ͣ������ϵ����

GGSJQ               DATE,                    -- ����ʱ����
GGSJZ               DATE,                    -- ����ʱ��ֹ
GGYY                VARCHAR2(2000),          -- ����ԭ��
GGSQRID             NUMBER(10),              -- ����������ID
GGPZRID             NUMBER(10),              -- ������׼��ID
GGFILEID            VARCHAR2(200),           -- ���ĸ���ID

YQSJ                DATE,                    -- ����ʱ��
YQYY                VARCHAR2(2000),          -- ����ԭ��
YQSQRID             NUMBER(10),              -- ����������ID
YQPZRID             NUMBER(10),              -- ������׼��ID
YQFILEID            VARCHAR2(200),           -- ���ڸ���ID

YXBYJ               VARCHAR2(2000),          -- Ӫ�����������ʱ���ޣ�
YYBFILEID           VARCHAR2(200),           -- Ӫ������������ʱ���ޣ�
YXBPZ               VARCHAR2(20),            -- Ӫ������׼����ʱ���ޣ�
YXBSJ               DATE,                    -- Ӫ����ʱ�䣨��ʱ���ޣ�

PDSYJ               VARCHAR2(2000),          -- ��������
PDSFILEID           VARCHAR2(200),           -- ���������
PDSPZ               VARCHAR2(20),            -- �������׼
PDSSJ               DATE,                    -- �����ʱ��

SJBYJ               VARCHAR2(2000),          -- �������������ʱ���ޣ�
SJBFILEID           VARCHAR2(200),           -- ��������������ʱ���ޣ�
SJBPZ               VARCHAR2(20),            -- ��������׼����ʱ���ޣ�
SJBSJ               DATE,                    -- ������ʱ�䣨��ʱ���ޣ�

JLDYJ               VARCHAR2(2000),          -- ���쵼�������ʱ���ޣ�
JLDFILEID           VARCHAR2(200),           -- ���쵼��������ʱ���ޣ�
JLDPZ               VARCHAR2(20),            -- ���쵼��׼����ʱ���ޣ�
JLDSJ               DATE,                    -- ���쵼ʱ�䣨��ʱ���ޣ�

SLDYJ               VARCHAR2(2000),          -- ���쵼�����δ�ã�
SLDFILEID           VARCHAR2(200),           -- ���쵼������δ�ã�
SLDPZ               VARCHAR2(20),            -- ���쵼��׼��δ�ã�
SLDSJ               DATE,                    -- ���쵼ʱ�䣨δ�ã�

FSPFYJ              VARCHAR2(2000),          -- ��ʽ�������
FSPFFILEID          VARCHAR2(200),           -- ��ʽ��������
FSPFYID             NUMBER(10),              -- ��ʽԱID
FSPFSJ              DATE,                    -- ��ʽ����ʱ��
FSPFPZ              VARCHAR2(20),            -- ��ʽ������׼
FSPFJHID            NUMBER(10),              -- ��ʽ����У��ԱID
FSPFJHSJ            DATE,                    -- ��ʽ����У��ʱ��
FSPFJHPZ            VARCHAR2(20),            -- ��ʽ����У����׼

JDPFYJ              VARCHAR2(2000),          -- �̵��������
JDPFFILEID          VARCHAR2(200),           -- �̵���������
JDPFYID             NUMBER(10),              -- �̵�ԱID
JDPFPZ              VARCHAR2(20),            -- �̵�������׼
JDPFSJ              DATE,                    -- �̵�����ʱ��
JDPFJHID            NUMBER(10),              -- �̵�����У��ID
JDPFJHSJ            DATE,                    -- �̵�����У��ʱ��
JDPFJHPZ            VARCHAR2(20),            -- �̵�����У����׼

DDSYJ               VARCHAR2(2000),          -- ���������
DDSFILEID           VARCHAR2(200),           -- ����������ID
DDSPZ               VARCHAR2(20),            -- ��������׼
DDSSJ               DATE,                    -- ������ʱ��

DDZYJ               VARCHAR2(2000),          -- ���������
DDZFILEID           VARCHAR2(200),           -- �����鸽��ID
DDZPZ               VARCHAR2(20),            -- ��������׼
DDZSJ               DATE,                    -- ������ʱ��

DDYYJ               VARCHAR2(2000),          -- ����Ա���
DDYFILEID           VARCHAR2(200),           -- ����Ա����ID
DDYPZ               VARCHAR2(20),            -- ����Ա��׼
DDYSJ               DATE,                    -- ����Աʱ��

YGSJ                DATE,                    -- Ԥ��ʱ��
XLSJ                DATE,                    -- ����ʱ��
TDSJ                DATE,                    -- ͣ��ʱ��
KGSJ                DATE,                    -- ����ʱ��
WGSJ                DATE,                    -- �깤ʱ��
FDSJ                DATE,                    -- ����ʱ��
YGFZRID             NUMBER(10),              -- Ԥ��Է�������ID
XLFZRID             NUMBER(10),              -- ����Է�������ID
TDFZRID             NUMBER(10),              -- ͣ��Է�������ID
KGFZRID             NUMBER(10),              -- �����Է�������ID
WGFZRID             NUMBER(10),              -- �깤�Է�������ID
FDFZRID             NUMBER(10),              -- ����Է�������ID
YGDDYID             NUMBER(10),              -- Ԥ�浱ֵ����ԱID
XLDDYID             NUMBER(10),              -- ���ֵ����ԱID
TDDDYID             NUMBER(10),              -- ͣ�統ֵ����ԱID
KGDDYID             NUMBER(10),              -- ������ֵ����ԱID
WGDDYID             NUMBER(10),              -- �깤��ֵ����ԱID
FDDDYID             NUMBER(10),              -- ���統ֵ����ԱID

BZ                  VARCHAR2(4000),          -- ��ע
BZFILEID            VARCHAR2(200),           -- ��ע����

ZFYY                VARCHAR2(2000),          -- ����ԭ��
ZFFILEID            VARCHAR2(200),           -- ���ϸ���
ZFRID               NUMBER(10),              -- ������ID
ZFSJ                DATE,                    -- ����ʱ��
WCBZ                VARCHAR2(20),            -- ��ɱ�־ 0:δ��� 1������� 2��������
LSBZ                VARCHAR2(20),            -- ��ʷ��־ 0:δת��ʷ 1:ת��ʷ

EXT1                VARCHAR2(400),           -- �����ֶ�1 
EXT2                VARCHAR2(400),           -- �����ֶ�2 
EXT3                VARCHAR2(400),           -- �����ֶ�3 
EXT4                VARCHAR2(400),           -- �����ֶ�4 
EXT5                VARCHAR2(400),           -- �����ֶ�5 
EXT6                VARCHAR2(400),           -- �����ֶ�6 
EXT7                VARCHAR2(400),           -- �����ֶ�7 
EXT8                VARCHAR2(400),           -- �����ֶ�8 
EXT9                VARCHAR2(400),           -- �����ֶ�9 
EXT10               VARCHAR2(400),           -- �����ֶ�10
EXT11               VARCHAR2(400),           -- �����ֶ�11
EXT12               VARCHAR2(400),           -- �����ֶ�12
EXT13               VARCHAR2(400),           -- �����ֶ�13
EXT14               VARCHAR2(400),           -- �����ֶ�14
EXT15               VARCHAR2(400),           -- �����ֶ�15
EXT16               VARCHAR2(400),           -- �����ֶ�16
EXT17               VARCHAR2(400),           -- �����ֶ�17
EXT18               VARCHAR2(400),           -- �����ֶ�18
EXT19               VARCHAR2(400),           -- �����ֶ�19
EXT20               VARCHAR2(400),           -- �����ֶ�20
EXT22               VARCHAR2(400),           -- �����ֶ�21
EXT21               VARCHAR2(400),           -- �����ֶ�22
EXT23               VARCHAR2(400),           -- �����ֶ�23
EXT24               VARCHAR2(400),           -- �����ֶ�24
EXT25               VARCHAR2(400),           -- �����ֶ�25
EXT26               VARCHAR2(400),           -- �����ֶ�26
EXT27               VARCHAR2(400),           -- �����ֶ�27
EXT28               VARCHAR2(400),           -- �����ֶ�28
EXT29               VARCHAR2(400),           -- �����ֶ�29
EXT30               VARCHAR2(400)            -- �����ֶ�30
)


/*
 * ˵�����ص�����Ʊ
 * ���򣺺���
 * ʱ�䣺2005-10-24
 */


CREATE TABLE WORKFLOW_YHTDJXP                --��Ϫ�ص�ͣ�����Ʊ
(
ID                  NUMBER(20),              -- ���(���������)
PH                  VARCHAR2(20),            -- Ʊ��

TXRID               NUMBER(10),              -- ��д��ID
TXSJ                DATE,                    -- ��дʱ��
SQRID               NUMBER(10),              -- ������ID
SLRID               NUMBER(10),              -- ������ID
SBID                VARCHAR2(2000),          -- �豸ID
SBFILEID            VARCHAR2(200),           -- �豸����
AQCS                VARCHAR2(2000),          -- ��ȫ��ʩ
AQCSFILEID          VARCHAR2(200),           -- ��ȫ��ʩ����
GZNR                VARCHAR2(2000),          -- ��������
GZNRFILEID          VARCHAR2(200),           -- �������ݸ���
TDFW                VARCHAR2(2000),          -- ͣ�緶Χ
TDFWFILEID          VARCHAR2(200),           -- ͣ�緶Χ����
JHSJQ               DATE,                    -- �ƻ�ʱ����
JHSJZ               DATE,                    -- �ƻ�ʱ��ֹ

GGSJQ               DATE,                    -- ����ʱ����
GGSJZ               DATE,                    -- ����ʱ��ֹ
GGYY                VARCHAR2(2000),          -- ����ԭ��
GGSQRID             NUMBER(10),              -- ����������ID
GGPZRID             NUMBER(10),              -- ������׼��ID
GGFILEID            NUMBER(10),              -- ���ĸ���ID

YQSJ                DATE,                    -- ����ʱ��
YQSQRID             NUMBER(10),              -- ������ID
YQYY                VARCHAR2(2000),          -- ����ԭ��
YQPZRID             NUMBER(10),              -- ������׼��ID
YQYJ                VARCHAR2(200),           -- �������
YQFILEID            NUMBER(10),              -- ���ڸ���ID
YQPZ                VARCHAR2(20),            -- ������׼
LXFF                VARCHAR2(500),           -- ͣ������ϵ����

YXBSLRHPSQRJN       VARCHAR2(200),           -- Ӫ���������˺��������˼���
YXBSLRHPJDSX        VARCHAR2(200),           -- Ӫ���������˺�����������
YXBSLRHRTZ          VARCHAR2(200),           -- Ӫ���������˺���ͣ��֪ͨ
YXBSLRHPSBCQ        VARCHAR2(200),           -- Ӫ���������˺���ͣ���豸��Ȩ����
YXBSLRHPCZFS        VARCHAR2(200),           -- Ӫ���������˺���ͣ�������ʽ
YXBSLRQTSX          VARCHAR2(200),           -- Ӫ���������˺�����������
YXBSLRHPSJ          DATE,                    -- Ӫ���������˺���ʱ��
YXBSLRID            NUMBER(10),              -- Ӫ����������ID  
YXBLDFHYJ           VARCHAR2(2000),          -- Ӫ�����쵼�������
YXBLDFHFILEID           VARCHAR2(200),           -- Ӫ�����쵼���ϸ���
YXBLDFHPZ           VARCHAR2(20),            -- Ӫ�����쵼������׼
YXBLDFHSJ           DATE,                    -- Ӫ�����쵼����ʱ��

GSSJBSPYJ           VARCHAR2(2000),          -- ��˾�������������
GSSJBSPFEID         VARCHAR2(200),           -- ��˾��������������
GSSJBSPPZ           VARCHAR2(20),            -- ��˾������������׼
GSSJBSPSJ           DATE,                    -- ��˾����������ʱ��
GSZGSPYJ            VARCHAR2(2000),          -- ��˾�����������
GSZGSPFIEID         VARCHAR2(200),           -- ��˾������������
GSZGSPPZ            VARCHAR2(20),            -- ��˾����������׼
GSZHSPSJ            DATE,                    -- ��˾��������ʱ��
XDPFYJ              VARCHAR2(2000),          -- �ص��������
XDPFFIELID          VARCHAR2(200),           -- �ص���������
XDPFPZ              VARCHAR2(20),            -- �ص�������׼
XDPFSJ              DATE,                    -- �ص�����ʱ��
FSPFYJ              VARCHAR2(2000),          -- ��ʽ�������
FSPFFILEID          VARCHAR2(200),           -- ��ʽ��������
FSPFYID             NUMBER(10),              -- ��ʽԱID
FSPFSJ              DATE,                    -- ��ʽ����ʱ��
FSPFPZ              VARCHAR2(20),            -- ��ʽ������׼
FSPFJH              NUMBER(10),              -- ��ʽ����У��
FSPFJHSJ            DATE,                    -- ��ʽ����У��ʱ��
JDPFYJ              VARCHAR2(2000),          -- �̵��������
JDPFFILEID          VARCHAR2(200),           -- �̵���������
JDPFYID             NUMBER(10),              -- �̵�ԱID
JDPFPZ              VARCHAR2(20),            -- �̵�������׼
JDPFJHID            NUMBER(10),              -- �̵�����У��
DDSYJ               VARCHAR2(2000),          -- ���������
DDSFILEID           VARCHAR2(200),           -- ����������
DDSPZ               VARCHAR2(20),            -- ��������׼
DDSSJ               DATE,                    -- ������ʱ��
DDZYJ               VARCHAR2(2000),          -- ���������
DDZFILEID           VARCHAR2(200),           -- �����鸽��
DDZPZ               VARCHAR2(20),            -- ��������׼
DDZSJ               DATE,                    -- ������ʱ��
DDYYJ               VARCHAR2(2000),          -- ����Ա���
DDYFILEID           VARCHAR2(200),           -- ����Ա����
DDYPZ               VARCHAR2(20),            -- ����Ա��׼
DDYSJ               DATE,                    -- ����Աʱ��

YGSJ                DATE,                    -- Ԥ��ʱ��
XLSJ                DATE,                    -- ����ʱ��
TDSJ                DATE,                    -- ͣ��ʱ��
KGSJ                DATE,                    -- ����ʱ��
WGSJ                DATE,                    -- �깤ʱ��
FDSJ                DATE,                    -- ����ʱ��
YGFZRID             NUMBER(10),              -- Ԥ��Է�������ID
XLFZRID             NUMBER(10),              -- ����Է�������ID
TDFZRID             NUMBER(10),              -- ͣ��Է�������ID
KGFZRID             NUMBER(10),              -- �����Է�������ID
WGFZRID             NUMBER(10),              -- �깤�Է�������ID
FDFZRID             NUMBER(10),              -- ����Է�������ID
YGDDYID             NUMBER(10),              -- Ԥ�浱ֵ����Ա
XLDDYID             NUMBER(10),              -- ���ֵ����Ա
TDDDYID             NUMBER(10),              -- ͣ�統ֵ����Ա
KGDDYID             NUMBER(10),              -- ������ֵ����Ա
WGDDYID             NUMBER(10),              -- �깤��ֵ����Ա
FDDDYID             NUMBER(10),              -- ���統ֵ����Ա
BZ                  VARCHAR2(4000),          -- ��ע
BZFILEID            VARCHAR2(200),           -- ��ע����
ZFYY                VARCHAR2(2000),          -- ����ԭ��
ZFFILEID            VARCHAR2(200),           -- ���ϸ���
ZFRID               NUMBER(10),              -- ������ID
ZFSJ                DATE,                    -- ����ʱ��
WCBZ                VARCHAR2(20),            -- ��ɱ�־ 0:δ��� 1������� 2��������
LSBZ                VARCHAR2(20),            -- ��ʷ��־ 0:δת��ʷ 1:ת��ʷ

EXT1                VARCHAR2(400),           -- �����ֶ�1 
EXT2                VARCHAR2(400),           -- �����ֶ�2 
EXT3                VARCHAR2(400),           -- �����ֶ�3 
EXT4                VARCHAR2(400),           -- �����ֶ�4 
EXT5                VARCHAR2(400),           -- �����ֶ�5 
EXT6                VARCHAR2(400),           -- �����ֶ�6 
EXT7                VARCHAR2(400),           -- �����ֶ�7 
EXT8                VARCHAR2(400),           -- �����ֶ�8 
EXT9                VARCHAR2(400),           -- �����ֶ�9 
EXT10               VARCHAR2(400),           -- �����ֶ�10
EXT11               VARCHAR2(400),           -- �����ֶ�11
EXT12               VARCHAR2(400),           -- �����ֶ�12
EXT13               VARCHAR2(400),           -- �����ֶ�13
EXT14               VARCHAR2(400),           -- �����ֶ�14
EXT15               VARCHAR2(400),           -- �����ֶ�15
EXT16               VARCHAR2(400),           -- �����ֶ�16
EXT17               VARCHAR2(400),           -- �����ֶ�17
EXT18               VARCHAR2(400),           -- �����ֶ�18
EXT19               VARCHAR2(400),           -- �����ֶ�19
EXT20               VARCHAR2(400),           -- �����ֶ�20
EXT21               VARCHAR2(400),           -- �����ֶ�21
EXT22               VARCHAR2(400),           -- �����ֶ�22
EXT23               VARCHAR2(400),           -- �����ֶ�23
EXT24               VARCHAR2(400),           -- �����ֶ�24
EXT25               VARCHAR2(400),           -- �����ֶ�25
EXT26               VARCHAR2(400),           -- �����ֶ�26
EXT27               VARCHAR2(400),           -- �����ֶ�27
EXT28               VARCHAR2(400),           -- �����ֶ�28
EXT29               VARCHAR2(400),           -- �����ֶ�29
EXT30               VARCHAR2(400)            -- �����ֶ�30
)