# spring-batch-poc
### Spring Batch with Spring boot and Quartz scheduler

<b><i>Tools used</i></b>
<ul>
  <li> Spring boot/ </li>
  <li> Spring batch  </li>
  <li> Quartz  </li>
  <li> Apache Derby Repository  </li>
  <li> Lombok  </li>
  <li> Logback </li>
  <li> JDK 8  </li>
</ul>

---
## Use case <b>Payroll Service </b>

* Calculate payroll based on employee's type.
* Payroll will be calculated by reading employee details from csv file using spring batch
* Payroll calculation carried out by automated jobs using quartz scheduler based on the employement type

<b>E.g.</b>
* For Regular employees, Payroll will be calculated on monthly basis
* For Contract employees on weekly basis
* For Intenship employees on daily basis
* For Freelancer employees on hourly basis

---
