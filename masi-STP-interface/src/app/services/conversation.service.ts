import { Injectable } from '@angular/core';

@Injectable()
export class ConversationService {

  constructor() { }

  setConversationContext(context) {
    localStorage.setItem("ConversationID", JSON.stringify(context));
  }

  getConversationContext() {
    return JSON.parse(localStorage.getItem("ConversationID"));
  }
}
