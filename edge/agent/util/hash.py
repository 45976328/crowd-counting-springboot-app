import secrets

from hashlib import pbkdf2_hmac
from os import urandom

#https://docs.python.org/3/library/hashlib.html#key-derivation
#https://docs.python.org/3/library/os.html#os.urandom

def hashMac (mac):

    mac_bytes = mac.encode('utf-8')

    iters = 100_000
    salt = urandom(16)
    #salt = secrets.token_bytes(16)
    #TODO static salt

    return pbkdf2_hmac('sha256', mac_bytes, salt * 2, iters).hex()
