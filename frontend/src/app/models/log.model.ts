export class Log {

  id: number;
  process: string;
  startDate: Date;
  endDate: Date;
  status: string;
  userLogin: string;

  constructor(id, process, startDate, endDate, status, userLogin) {
    this.id = id;
    this.process = process;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
    this.userLogin = userLogin;
  }

}
