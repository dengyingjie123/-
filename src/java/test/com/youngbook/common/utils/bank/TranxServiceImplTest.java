package com.youngbook.common.utils.bank;

import com.youngbook.common.ReturnObject;
import junit.framework.TestCase;

public class TranxServiceImplTest extends TestCase {

    TranxServiceImpl tranxService = new TranxServiceImpl();
    public void testDealResponse() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><AIPG>\n" +
                "  <INFO>\n" +
                "    <TRX_CODE>211003</TRX_CODE>\n" +
                "    <VERSION>03</VERSION>\n" +
                "    <DATA_TYPE>02</DATA_TYPE>\n" +
                "    <REQ_SN>160601015152</REQ_SN>\n" +
                "    <RET_CODE>0000</RET_CODE>\n" +
                "    <ERR_MSG>提交成功</ERR_MSG>\n" +
                "    <SIGNED_MSG>880d636e125979c7894d14d3769c642d4306889abeb889639a69c023b77fef59895b3e8865d6ef319d5bb360e3136f1b05e89b57972dba12dc28360c6eac1c6610bd9adf41075108d9b8210c70eb7182f260af3c2112e28f40946134d6de106a205c8288af6bb73ea5ef029ec07a29770ff2f3a7ff049c55053bdfcb107c0d92f147f9308c82a75c0c0a7857090ac91043a3ffda4f8e5bd394c9c5e18d5fa541e6e38a6c65d5011c91ab65b7e0bd1514c22a866ca6706d6a588c16d7bb425cedd5c6cfabff8cc4304e8fe69b22d6f460c6e3984367377de7f2457156cb5b3d1a6f308c22930937b54c2399fd172d1fb8a8a3f1356123ca70788149f3fa41c887</SIGNED_MSG>\n" +
                "  </INFO>\n" +
                "  <VALIDRET>\n" +
                "    <RET_CODE>3066</RET_CODE>\n" +
                "    <ERR_MSG>渠道不支持，交易无法进行</ERR_MSG>\n" +
                "  </VALIDRET>\n" +
                "</AIPG>";

        ReturnObject returnObject = tranxService.dealResponse(xml);

        System.out.println(returnObject);
    }
}