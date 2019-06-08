

class User:

    def __init__(self, email, password, nickname):
        self.email = email
        self.password = password
        self.nickname = nickname

    def __str__(self):
        return User(
            email=self.email,
            password=self.password,
            nickname=self.nickname,
        )
        #return f'User(email={self.email}, password={self.password}, nickname={self.nickname})'

    @staticmethod
    def fromDict(userDict):
        return User(
            email=userDict['email'],
            password=userDict['password'],
            nickname=userDict['nickname'],
        )
 
