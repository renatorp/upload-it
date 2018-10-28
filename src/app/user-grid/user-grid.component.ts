import { Component, OnInit, Input } from '@angular/core';
import { User } from '../model/user';

@Component({
  selector: 'app-user-grid',
  templateUrl: './user-grid.component.html',
  styleUrls: ['./user-grid.component.css']
})
export class UserGridComponent implements OnInit {

  public users: User[] = [];
  newUser: User;

  constructor() { }

  ngOnInit() {

    this.newUser = new User();

    const user: User = new User();
    user.id = 1;
    user.name = 'admin';
    this.users.push(user);
  }

  deleteUser(id): void {
    this.users = this.users.filter(u => u.id !== id);
  }

  addUser(): void {
    this.users.push(this.newUser);
    this.newUser = new User();
  }
}
