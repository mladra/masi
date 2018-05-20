import { Injectable } from '@angular/core';
import { Button } from 'protractor';

@Injectable()
export class MessageParser {

  constructor() { }

  getCategories(messages: string[]) {
    const output: string[][] = new Array();
    for (const message of messages) {
      const categories = [];
      const parts: string[] = message.split(' ');
      for (const part of parts) {
        if (part.startsWith('&')) {
          let cat = part.replace(/&/g, '');
          cat = cat.replace(/_/g, ' ');
          categories.push(cat);
        }
      }
      output.push(categories);
    }
    return output;
  }

  getLinks(messages: string[]) {
    const links: string[] = new Array();
    for (const message of messages) {
      const parts: string[] = message.split(' ');
      for (const part of parts) {
        if (part.startsWith('&link&')) {
          const link = part.replace(/&link&/g, '');
          links.push(link);
        }
      }
    }
    return links;
  }

  getParsedResponse(messages: string[]) {
    const output: string[] = [];
    for (const message of messages) {
      let singleMessage = '';
      const parts: string[] = message.split(' ');
      for (const part of parts) {
        if (!part.startsWith('&')) {
          singleMessage += ' ' + part;
        }
      }
      if (singleMessage.length > 0) {
        output.push(singleMessage);
      }
    }
    return output;
  }
}
