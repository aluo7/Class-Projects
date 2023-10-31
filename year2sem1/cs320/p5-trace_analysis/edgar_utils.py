import re, netaddr
import pandas as pd
from bisect import bisect

ips = pd.read_csv("ip2location.csv")

def lookup_region(ip):
    ip = re.sub("[a-z]", "0", ip)
    
    int_rep = int(netaddr.IPAddress(ip))
    
    idx = bisect(ips["low"], int_rep) - 1
    return ips.iloc[idx]["region"]

class Filing:
    def __init__(self, html):
        self.dates = re.findall(r"(?:19|20)\d{2}-\d{2}-\d{2}", html)
        if re.findall(r"SIC=(\d*)", html):
            self.sic = int(re.findall(r"SIC=(\d*)", html)[0])
        else:
            self.sic = None
        self.addresses = []
        for addr_html in re.findall(r'<div class="mailer">([\s\S]+?)</div>', html):
            lines = []
            for line in re.findall(r'<span class="mailerAddress">([\s\S]+?)</span>', addr_html):
                lines.append(line.strip())
            if lines:
                self.addresses.append("\n".join(lines))

    def state(self):
        for addr in self.addresses:
            if re.findall(r'([A-Z]{2})\s[0-9]{5}', addr):
                return re.findall(r'([A-Z]{2})\s[0-9]{5}', addr)[0]
        return None
    