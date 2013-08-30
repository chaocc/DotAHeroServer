/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */

#import <Foundation/Foundation.h>

#import "TProtocol.h"
#import "TApplicationException.h"
#import "TProtocolUtil.h"
#import "TProcessor.h"

#import "ThriftFlattenedEsObject.h"
#import "ThriftServerGame.h"

@interface ThriftFindGamesResponse : NSObject <NSCoding> {
  NSArray * __games;

  BOOL __games_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, strong, getter=games, setter=setGames:) NSArray * games;
#endif

- (id) initWithGames: (NSArray *) games;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (NSArray *) games;
- (void) setGames: (NSArray *) games;
- (BOOL) gamesIsSet;

@end

@interface ThriftFindGamesResponseConstants : NSObject {
}
@end