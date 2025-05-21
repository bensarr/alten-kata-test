import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, delay } from 'rxjs';
import {ContactRequest} from "./contact.model";

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/contact';

  public sendMessage(contactData: ContactRequest): Observable<boolean> {
    return of(true).pipe(delay(1000));
  }
}
