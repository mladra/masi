import { Component, OnInit, AfterViewChecked, ViewChild, ElementRef } from '@angular/core';
import { Message } from './models/message';
import { ConversationService } from './services/conversation.service';
import { MessageService } from './services/message.service';
import { MessageParser } from './services/message.parser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewChecked {

  @ViewChild('scrollable') private scrollable: ElementRef;

  private messages: Message[];
  private userMessage: string;
  private botTyping: boolean;
  private connectionError: boolean;
  private windowRef: Window;

  constructor(
    private conversationService: ConversationService,
    private messageService: MessageService,
    private messageParser: MessageParser
  ) {
    this.messages = [];
    this.botTyping = false;
    this.connectionError = true;
    this.userMessage = '';
  }

  ngOnInit() {
    this.refreshConnection();
    this.windowRef = window.open('https://www.amazon.com');
    this.windowRef.blur();
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  sendMessage(msg) {
    if (msg !== null && msg.trim().length !== 0 && !this.botTyping) {
      this.botTyping = true;

      const messageToSent = new Message();
      messageToSent.author = 'user';
      messageToSent.context = this.conversationService.getConversationContext();
      messageToSent.message = msg.trim();
      messageToSent.response = [];
      messageToSent.url = '';

      this.messages.push(messageToSent);

      this.messageService.sendMessage(messageToSent).subscribe(
        response => {
          const responseMsg = response.body;
          responseMsg.author = 'bot';
          this.botTyping = false;
          this.messages.push(responseMsg);
          if (responseMsg.url) {
            this.windowRef.location.href = responseMsg.url;
          }
        },
        error => {
          this.botTyping = false;
          this.messages.push({
            author: 'bot',
            message: 'Ohh... Sorry, There was an unexpected error in my system. Could you please send me your message again?',
            response: null,
            url: null,
            context: null,
            categories: []});
        }
      );
    }
  }

  refreshConnection() {
    const msg = new Message();
    msg.message = '';
    this.messageService.sendMessage(msg).subscribe(
      response => {
        const responseMsg = response.body as Message;
        responseMsg.author = 'bot';
        this.conversationService.setConversationContext(responseMsg.context);
        this.messages.push(responseMsg);
        this.connectionError = false;
      },
      error => {
        this.connectionError = true;
      }
    );
  }

  chooseCategory(category) {
    const msg = new Message();
    msg.author = 'user';
    msg.context = this.conversationService.getConversationContext();
    msg.message = 'I want a book about ' + category; 
    this.messageService.sendMessage(msg).subscribe(
        response => {
          const responseMsg = response.body;
          responseMsg.author = 'bot';
          this.botTyping = false;
          this.messages.push(responseMsg);
          if (responseMsg.url) {
            this.windowRef.location.href = responseMsg.url;
          }
        },
        error => {
          this.botTyping = false;
          this.messages.push({
            author: 'bot',
            message: 'Ohh... Sorry, There was an unexpected error in my system. Could you please send me your message again?',
            response: null,
            url: null,
            context: null,
            categories: []});
        }
      );
  }

  scrollToBottom() {
    try {
      this.scrollable.nativeElement.scrollTop = this.scrollable.nativeElement.scrollHeight;
    } catch (err) {
      console.log(err);
    }
  }

  handleKeyUp(event) {
    if (event.keyCode === 13) {
      if (this.userMessage !== null && this.userMessage.trim().length !== 0 && !this.botTyping) {
        this.sendMessage(this.userMessage);
        this.userMessage = '';
      }
    }
  }
}
