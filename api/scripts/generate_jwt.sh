#!/bin/bash

# Function to install openssl based on the distro
install_openssl() {
    if [ -f /etc/debian_version ]; then
        echo "Detected Debian-based distribution."
        read -p "Do you want to install openssl? (y/n): " choice
        if [ "$choice" = "y" ]; then
            sudo apt update && sudo apt install -y openssl
        else
            echo "openssl installation skipped."
            exit 1
        fi
    elif [ -f /etc/lsb-release ] && grep -q 'Ubuntu' /etc/lsb-release; then
        echo "Detected Ubuntu distribution."
        read -p "Do you want to install openssl? (y/n): " choice
        if [ "$choice" = "y" ]; then
            sudo apt update && sudo apt install -y openssl
        else
            echo "openssl installation skipped."
            exit 1
        fi
    elif [ -f /etc/os-release ] && grep -q 'openSUSE' /etc/os-release; then
        echo "Detected openSUSE distribution."
        read -p "Do you want to install openssl? (y/n): " choice
        if [ "$choice" = "y" ]; then
            sudo zypper install -y openssl
        else
            echo "openssl installation skipped."
            exit 1
        fi
    elif [ -f /etc/redhat-release ] && (grep -q 'Rocky' /etc/redhat-release || grep -q 'Alma' /etc/redhat-release); then
        echo "Detected Rocky Linux or AlmaLinux distribution."
        read -p "Do you want to install openssl? (y/n): " choice
        if [ "$choice" = "y" ]; then
            sudo yum install -y openssl
        else
            echo "openssl installation skipped."
            exit 1
        fi
    else
        echo "Unsupported distribution. Please install openssl manually."
        exit 1
    fi
}

# Check if openssl is installed
if ! command -v openssl &> /dev/null
then
    echo "openssl could not be found."
    install_openssl
fi

# Generate JWT_SECRET
echo "JWT_SECRET=$(openssl rand -base64 64)"
