import Presentations from '/imports/api/presentations/collection';
import { logger } from '/imports/startup/server/logger';

Meteor.publish('presentations', function (meetingId) {
  logger.info(`publishing presentations for ${meetingId}`);
  return Presentations.find({
    meetingId: meetingId,
  });
});
