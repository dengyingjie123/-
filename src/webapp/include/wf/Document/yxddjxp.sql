/*
 * ˵�����ص�����Ʊ
 * ���򣺺���
 * ʱ�䣺2005-10-24
 */


CREATE TABLE WORKFLOW_DDJXP                  --��Ϫ�ص�ͣ�����Ʊ
{
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
YQSJ                DATE,                    -- ����ʱ��
YQSQRID             NUMBER(10),              -- ������
YQYY                VARCHAR2(2000),          -- ����ԭ��
YQPZRID             NUMBER(10),              -- ������׼��
YQYJ                VARCHAR2(200),           -- �������
YQFILEID            NUMBER(10),              -- ���ڸ���
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
YXBLDFHYJ           VARCHAR2(200),           -- Ӫ�����쵼���ϸ���
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
XDPFYJ              VARCHAR2(200),           -- �ص���������
XDPFPZ              VARCHAR2(20),            -- �ص�������׼
XDPFYJ              DATE,                    -- �ص�����ʱ��
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
YGFZRID             NUMBER(10),              -- Ԥ��Է�������
XLFZRID             NUMBER(10),              -- ����Է�������
TDFZRID             NUMBER(10),              -- ͣ��Է�������
KGFZRID             NUMBER(10),              -- �����Է�������
WGFZRID             NUMBER(10),              -- �깤�Է�������
FDFZRID             NUMBER(10),              -- ����Է�������
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
WCBZ                VARCHAR2(20),            -- ��ɱ�־
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
EXT22               VARCHAR2(400),           -- �����ֶ�22
EXT23               VARCHAR2(400),           -- �����ֶ�23
EXT24               VARCHAR2(400),           -- �����ֶ�24
EXT25               VARCHAR2(400),           -- �����ֶ�25
EXT26               VARCHAR2(400),           -- �����ֶ�26
EXT27               VARCHAR2(400),           -- �����ֶ�27
EXT28               VARCHAR2(400),           -- �����ֶ�28
EXT29               VARCHAR2(400),           -- �����ֶ�29
EXT30               VARCHAR2(400)            -- �����ֶ�30

}