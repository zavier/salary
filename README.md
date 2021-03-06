# 薪水支付系统的初步规格说明

该系统由一个公司雇员数据库以及和雇员相关的数据（比如:工作时间卡）组成。该系统必须为每个雇员支付薪水。系统必须按照规定的方法准时地给雇员支付正确数目的薪水。
同时，必须从雇员的薪水中减去各种扣款。

- 有些雇员是钟点工。会按照他们雇员记录中每小时报酬字段的值对他们进行支付。他们每天会提交工作时间卡，其中记录了日期以及工作小时数。如果他们每天工作超过8小时，那么超过的部分会按照正常报酬的1.5倍进行支付。每周五对他们进行支付。
- 有些雇员完全以月薪进行支付。每个月的最后一个工作日对他们进行支付。在他们的雇员记录中有一个月薪字段。
- 同时，对于一些带薪雇员，会根据他们的销售情况，支付给他们一定数量的酬金。他们会提交销售凭条，其中记录了销售的日期和数量。在他们的雇员记录中有一个酬金字段。每隔一周的周五对他们进行支付。
- 雇员可以选择支付方式。可以选择把支付支票邮寄到他们制定的邮政地址；也可以把支票保存在出纳人员那里随时支取；或者要求将薪水直接存入他们指定的银行账户。
- 一些雇员会加入协会。在他们的雇员记录中有一个每周应付款项字段。这些应付款必须要从他们的薪水中扣除。协会有时也会针对单个协会成员征收服务费。协会每周会提交这些服务费用，服务费用必须要从相应雇员的下个月薪水总额中扣除。
- 薪水支付应用程序每个工作日运行一次，并在当天为相应的雇员进行支付。系统会被告知雇员的支付日期，这样它会计算从雇员上次支付日期到规定的本次支付日期间应支付的数额。



## 用例

### 用例1：增加新雇员

使用AddEmp操作可以增加新的雇员。该操作包含有雇员的名字、地址以及分配的雇员号。该操作有如下3种形式:

```tex
AddEmp <EmpId> "<name>" "<address>" H <hourly-rate>
AddEmp <EmpId> "<name>" "<addredd>" S <monthly-salary>
AddEmp <EmpId> "<name>" "<addredd>" C <monthly-salary> <commission-rate>
```

异常情况：描述操作的结构中有错误

如果结构不正确，会将它打印出来，并且不进行任何处理。



### 用例2：删除雇员

使用DelEmp操作来删除雇员。该操作使用如下形式：

`DelEmp <EmpId>`   当执行该操作时，会删除对应的雇员记录

异常情况：无效或者位置的EmpID

如果EmpId字段不具有正常的结构，或者它没有引用到一条有效的雇员记录，那么会在一条错误消息中把它打印出来，并且不进行其他处理。



### 用例3：登记时间卡

执行TimeCard操作时，系统会创建一个时间卡记录，并把该记录和对应的雇员记录关联起来。

`TimeCard <EmpID> <date> <hours>`

异常情况1：所选中的雇员不是钟点工

系统会打印一条适当的错误消息，并且不进行进一步的处理

异常情况2：描述操作的结构中有错误

系统会打印一条适当的错误消息，并且不进行进一步的处理



### 用例4：登记销售凭条

执行SalesReceipt操作时，系统会创建一个新的销售凭条记录，并把该记录和对应的应支付薪金的雇员联系起来。

`SaledReceipt <EmpID> <date> <amount>`

异常情况1：所选择的雇员不是应该支付薪金的

系统会打印一条适当的错误消息，并且不进行进一步的处理

异常情况2：描述操作的结构中有错误

系统会打印一条适当的错误消息，并且不进行进一步的处理



### 用例5：登记协会服务费

执行这个操作时，系统会创建一个服务费用记录，并把该记录和对应的协会成员关联起来。

`ServiceCharge <memberId> <amount>`

异常情况：描述操作的结构不是良好组织的

如果该操作不是良好组织的，或者memberId引用到一个不存在的协会成员，那么会把该操作在一条适当的错误消息中打印出来



### 用例6：更改雇员明细

执行这个操作时，系统会更改对应雇员记录的详细信息之一。该操作有几个可能的变体：

```
ChgEmp <EmpID> Name <name>  更改雇员名
ChgEmp <EmpID> Address <address> 更改雇员地址
ChgEmp <EmpID> Hourly <hourlyRate> 更新每小时报酬
ChgEmp <EmpID> Salaryied <salary> 更改薪水
ChgEmp <EmpID> Commissioned <salary> <rate> 更新酬金
ChgEmp <EmpID> Hold  持有支票
ChgEmp <EmpID> Direct <bank> <account> 直接存款
ChgEmp <EmpID> Mail <address> 邮寄支票
ChgEmp <EmpID> Member <memberID> Dues <rate> 使雇员加入协会
ChgEmp <EmpID> NoMember 从协会去掉雇员
```

异常情况：操作错误

如果结构不正确，或者EmpID没有引用到真正的雇员，或者memberID已经引用了一个成员，那么打印一条适当的错误，并且不进行进一步的处理



### 用例7：现在运行薪水支付系统

执行Payday操作时，系统会找到所有应该在指定日期进行支付的雇员。接着系统确定出他们的应扣款项，并根据他们所选择的支付方式对他们进行支付。

`Payday <date>`







