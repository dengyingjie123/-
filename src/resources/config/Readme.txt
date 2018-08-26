-- 设置测试环境
delete from system_kv where id='CDB91413-9C1A-4662-A592-5BD111E73966';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('CDB91413-9C1A-4662-A592-5BD111E73966', 'wsi.url', 'http://localhost:8080/core/wsi/Service_invoke', 'SystemConfig', NULL);
delete from system_kv where id='2BA0FAE3-B567-4CB2-ACDF-D6EE2E558375';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('2BA0FAE3-B567-4CB2-ACDF-D6EE2E558375', 'wsi.token', '1223', 'SystemConfig', NULL);
delete from system_kv where id='F25C8636-05D9-4A2E-B743-79997E16BAB5';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('F25C8636-05D9-4A2E-B743-79997E16BAB5', 'default.Password.Md5', '8543e494bc94d33aca1a97d9c099ba61', 'SystemConfig', NULL);

-- system.oa.sms.identityCode.url
delete from system_kv where id='2CB8F105-869E-4C2D-9C7F-3547CB154BC9';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('2CB8F105-869E-4C2D-9C7F-3547CB154BC9', 'system.oa.sms.identityCode.url', 'http://web.cr6868.com/asmx/smsservice.aspx', 'SystemConfig', NULL);

-- system.oa.sms.identityCode.unitId
delete from system_kv where id='DB8DD2BE-0400-4773-A2D7-66EB1815260D';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('DB8DD2BE-0400-4773-A2D7-66EB1815260D', 'system.oa.sms.identityCode.unitId', '', 'SystemConfig', NULL);

-- system.oa.sms.identityCode.userName
delete from system_kv where id='416962303C164217A7F2314F62F40BE8';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('416962303C164217A7F2314F62F40BE8', 'system.oa.sms.identityCode.userName', '8049391', 'SystemConfig', NULL);

-- system.oa.sms.identityCode.password
delete from system_kv where id='9760C1DEFCE24864BDE3B90545B93D5B';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('9760C1DEFCE24864BDE3B90545B93D5B', 'system.oa.sms.identityCode.password', 'C0352567117F06EAD7020ECA7DF5', 'SystemConfig', NULL);

-- bank.pay.allinpay.ertPath
delete from system_kv where id='BC7C55ED172E4CDC9111EF738CD0AF16';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('BC7C55ED172E4CDC9111EF738CD0AF16', 'bank.pay.allinpay.ertPath', 'D:/work/03_deploy/weblogic/config/allinpay/TLCert-test.cer', 'SystemConfig', NULL);

-- bank.pay.allinpay.pfxPath
delete from system_kv where id='10AFA797E479496BBD35DD80B5ABDE57';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('10AFA797E479496BBD35DD80B5ABDE57', 'bank.pay.allinpay.pfxPath', 'D:/work/03_deploy/weblogic/config/allinpay/20060400000044502.p12', 'SystemConfig', NULL);

-- bank.pay.allinpay.tltcerPath
delete from system_kv where id='4EF8A534651A46FF8F51775A1C1D445F';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('4EF8A534651A46FF8F51775A1C1D445F', 'bank.pay.allinpay.tltcerPath', 'D:/work/03_deploy/weblogic/config/allinpay/allinpay-pds.cer', 'SystemConfig', NULL);


-- bank.pay.allinpay.daifu.merchantId
delete from system_kv where id='C3EC6C1969714AFF9F34A054D02F74D2';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('C3EC6C1969714AFF9F34A054D02F74D2', 'bank.pay.allinpay.daifu.merchantId', '200604000000445', 'SystemConfig', NULL);

-- bank.pay.allinpay.userName
delete from system_kv where id='AFB2D334F17E4C93A2F240901D88D5E0';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('AFB2D334F17E4C93A2F240901D88D5E0', 'bank.pay.allinpay.userName', '20060400000044502', 'SystemConfig', NULL);


-- bank.pay.allinpay.password
delete from system_kv where id='9BF23CE29B3B4FA8B5E803596095C9CE';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('9BF23CE29B3B4FA8B5E803596095C9CE', 'bank.pay.allinpay.password', '`12qwe', 'SystemConfig', NULL);

-- bank.pay.allinpay.auth.url
delete from system_kv where id='';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('54C7ACC6EBA041A596D5B8059B6660F2', 'bank.pay.allinpay.auth.url', 'https://113.108.182.3/aipg/ProcessServlet', 'SystemConfig', NULL);

-- bank.pay.allinpay.callback.success.flag
delete from system_kv where id='4D9477949FCC4390953E1CE444CCB3AA';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('4D9477949FCC4390953E1CE444CCB3AA', 'bank.pay.allinpay.callback.success.flag', '0000', 'SystemConfig', NULL);

-- bank.pay.allinpay.merchantId
delete from system_kv where id='DD84F31D6FD34472B82AB08D763755F6';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('DD84F31D6FD34472B82AB08D763755F6', 'bank.pay.allinpay.merchantId', '100020091218001', 'SystemConfig', NULL);

-- bank.pay.allinpay.order.submit
delete from system_kv where id='FC503E5D1E0D4E918734CFEF9EA9B723';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('FC503E5D1E0D4E918734CFEF9EA9B723', 'bank.pay.allinpay.order.submit', 'http://ceshi.allinpay.com/gateway/index.do', 'SystemConfig', NULL);

-- bank.pay.allinpay.pfxPassword
delete from system_kv where id='4B37FDC1C9954A978C00A760DD03BC66';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('4B37FDC1C9954A978C00A760DD03BC66', 'bank.pay.allinpay.pfxPassword', '111111', 'SystemConfig', NULL);

-- bank.pay.allinpay.md5key
delete from system_kv where id='B3520C1AD105434C88A72587B23EDD73';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('B3520C1AD105434C88A72587B23EDD73', 'bank.pay.allinpay.md5key', '1234567890', 'SystemConfig', NULL);

-- web.default.saleId
delete from system_kv where id='54CBBE4EB80449B385EAE7AEBFBD5287';
INSERT INTO `system_kv` (`ID`, `K`, `V`, `GroupName`, `Orders`) VALUES ('54CBBE4EB80449B385EAE7AEBFBD5287', 'web.default.saleId', 'E4382AC8F3BB4994A84A482BCAD669B3', 'SystemConfig', NULL);