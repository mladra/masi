import { Injectable } from '@angular/core';
import { Button } from 'protractor';

@Injectable()
export class MessageParser {

  constructor() { }

  getCategories(message: string) {
    let output: string[] = [];
    const parts: string[] = message.split(' ');
    for (const part of parts) {
      if (part.startsWith('#')) {
        output.push(part.replace('#', ''));
      }
    }
    return output;
  }

  getParsedMessage(message) {
    let output: string = '';
    const parts: string[] = message.split(' ');
    for (let part of parts) {
      if (!part.startsWith('#')) {
        output += ' ' + part;
      }
    }
    return output;
  }
}
