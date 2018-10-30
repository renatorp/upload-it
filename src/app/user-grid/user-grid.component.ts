import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { ErrorHandlerService } from '../error-handler.service';

@Component({
  selector: 'app-user-grid',
  templateUrl: './user-grid.component.html',
  styleUrls: ['./user-grid.component.css']
})
export class UserGridComponent implements OnInit {

  public users: User[] = [];
  newUser: User;

  constructor(private userService: UserService, private errorHandler: ErrorHandlerService) { }

  ngOnInit() {
    this.newUser = new User();
    this.userService.findUsers().subscribe(
      response => {
        this.users = <User[]>response;
      },
      error => { this.errorHandler.handleError(error); }
    );
  }

  deleteUser(id): void {
    this.userService.deleteUser(id).subscribe(
      response => {
        this.users = this.users.filter(u => u.id !== id);
      },
      error => { this.errorHandler.handleError(error); }
    );
  }

  addUser(): void {
    this.userService.createUser(this.newUser).subscribe(
      response => {
        this.users.push(<User>response);
        this.newUser = new User();
      },
      error => { this.errorHandler.handleError(error); }
    );
  }

  isAdmin(): boolean {
    return localStorage.getItem('userId') === '1';
  }
}
