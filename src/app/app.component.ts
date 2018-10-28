import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'upload-it-ui';

  constructor(private router: Router) {
  }

  isAuthenticated() {
    const userId = localStorage.getItem('userId');
    return (userId) ? true : false;
  }

  logout() {
    localStorage.removeItem('userId');
    this.router.navigate(['/login']);
  }
}
