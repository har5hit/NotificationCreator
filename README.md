# NotificationCreator

Auto creation of Android notifications directly from data sent from Server using <b>Delegate</b> Pattern.


# Usage

This library aims to lessen boilerplate required to create notifications by accepting a data model `NotificationData` or any derived class of it.
 
`GenericDelegate` is a abstract class provided which handles generic notification which can be extended to make app specific modifications like `drawables`, `sound` etc 

Multiple Delegates can be created to handle different usecases and can be sent to NotificationCreationFactory instance which will handle delegation to appropriate delegate. Each delegate specify what type of payload they can handle by themselves in function `canHandle(type:String)`.
<br>

Refer AppDelegate class in Demo app for sample.


# Payload
```
NotificationData
{
  "type":"Generic"
  "title": "Test Message",
  "message": "Hello How are you",
  "channelId": "greeting",
  "channelName": "Greeting",
  "channelPriority": "max",
  "landingId": "main",
  "action": "android.intent.action.VIEW",
  "style": "bigPicture",
  "icon": "https://yourdomainaddress/jpgs/icon.jpg",
  "extras": {
    "bigPicture": "https://yourdomainaddress/jpgs/abc.jpg"
  },
  "actions": [
    {
      "label": "View",
      "drawable": "https://yourdomainaddress/jpgs/abc.jpg",
      "action": "view",
      "extras": {}
    }
  ]
}
```

* <b>type</b> - To distinguish which delegate should handle this payload.
 
* <b>title</b> - Title of the notification.

* <b>message</b> <i>(optional)</i> - Message body.

* <b>channelId,channelName</b> - Android channel stuff. [Notification Channels](https://developer.android.com/training/notify-user/channels)
 
* <b>channelPriority</b> - Priority of message. Values one of ("max", "high", "low", "min").
Default value set to Default priority

* <b>landingId</b> <i>(optional)</i> - The Id of the activity to be opened.

* <b>action</b> <i>(optional)</i> - The action for the intent to be opened.

<i><b>Attention</b>: landingId and action shouldn't be sent together. Only one of these attributes will be accepted, with landingId getting priority</i>

* <b>style</b> <i>(optional)</i> - One of ("message","bigPicture").

* <b>icon</b> <i>(optional)</i> - Icon of notification.

* <b>extras</b> <i>(optional)</i> - Extra Map<String,String> data for handling of notifications and to be sent as bundle to the intent created by landingId/landingAction.

* <b>actions</b> <i>(optional)</i> - List of actions to be shown in notification.
    * <b>label</b> - Label shown in action.
    * <b>drawable</b> <i>(optional)</i> - icon for the action.
    * <b>action</b> <i>(optional)</i> - Intent action of the intent created by landingId/landingAction.
    * <b>extras</b> <i>(optional)</i> - Extra Map<String,String> data for handling of notifications and to be sent as bundle to the intent created by landingId/landingAction.


## Customization

Generic delegate has functions which can be Overridden to modify notification data, builder and other changes specific to usecase/delegate.


## Demo

The project is a demo with a sample AppDelegate to test sample notifications. How delegate is created, connected to factory and how is notification data sent to factory for payload processing.




## License

```
Copyright 2019 Harshith Shetty

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```




