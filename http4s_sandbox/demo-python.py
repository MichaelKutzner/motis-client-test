#!/usr/bin/env -S uv run --script
# /// script
# dependencies = [
#     "example-client @ file://${PROJECT_ROOT}/python-client",
# ]
# ///

from example.client import MyObject, MyEither, MyLeft, MyRight
from example.client import ApiClient, Configuration, DefaultApi

def main():
    either = MyLeft(left='Hello, World!', value=2.71, type='MyLeft')
    print(f"Either: '{either.to_json()}'")
    print()
    o = MyObject.from_dict({'@id': 5, 'feature': either.to_dict()})
    assert(o is not None)
    print(f"Object: '{o}'")
    print(o.to_json())
    o2 = MyObject(id=123, value='Object (2)', feature=MyEither(MyRight(right='Hello', value='World')))
    print()
    print(f"Object(2): '{o2}'")
    print(f"Object(2).to_json(): '{o2.to_json()}'")

    resp = DefaultApi(ApiClient(Configuration(host='http://localhost:8000'))).echo_post(o2)
    print(f'Response: {resp.to_json()}')


if __name__ == '__main__':
    main()

