import React from "react";

const Logo = () => {
    return (
        <div>
            <svg
                width="124"
                height="50"
                viewBox="0 0 124 50"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
                className="logo"
                style={{ width: "124px", height: "50px" }}
            >
                <circle cx="25" cy="25" r="25" fill="#3A0CA3"></circle>
                <g clipPath="url(#clip0_8_2)">
                    <path
                        d="M36.715 22.165L25.465 13.165C25.332 13.0585 25.1667 13.0005 24.9963 13.0005C24.8258 13.0005 24.6605 13.0585 24.5275 13.165L13.2775 22.165C13.1561 22.2624 13.0681 22.3951 13.0255 22.5447C12.9829 22.6944 12.9879 22.8536 13.0398 23.0002C13.0917 23.1469 13.188 23.2738 13.3152 23.3634C13.4425 23.4529 13.5944 23.5006 13.75 23.5H15.25V36.25C15.25 36.4489 15.329 36.6397 15.4697 36.7803C15.6103 36.921 15.8011 37 16 37H31V35.5H28V28.75C28 28.5511 27.921 28.3603 27.7803 28.2197C27.6397 28.079 27.4489 28 27.25 28H22.75C22.5511 28 22.3603 28.079 22.2197 28.2197C22.079 28.3603 22 28.5511 22 28.75V35.5H16.75V23.5H29.5V22H15.8875L25 14.71L34.1125 22H34C33.8011 22 33.6103 22.079 33.4697 22.2197C33.329 22.3603 33.25 22.5511 33.25 22.75V27.25H34.75V23.5H36.25C36.405 23.4991 36.5558 23.4502 36.6819 23.3601C36.8079 23.27 36.903 23.143 36.9539 22.9967C37.0049 22.8504 37.0093 22.6918 36.9665 22.5429C36.9237 22.394 36.8359 22.262 36.715 22.165ZM23.5 29.5H26.5V35.5H23.5V29.5Z"
                        fill="white"
                    ></path>
                    <path
                        d="M31.75 32.9425L30.7825 31.975L29.725 33.0325L31.225 34.5325C31.3655 34.6722 31.5556 34.7506 31.7537 34.7506C31.9519 34.7506 32.142 34.6722 32.2825 34.5325L36.7825 30.0325L35.725 28.975L31.75 32.9425Z"
                        fill="white"
                    ></path>
                </g>
                <text
                    fill="#1E1E1E"
                    style={{ whiteSpace: "pre" }}
                    fontSize="20"
                    fontWeight="600"
                    letterSpacing="0em"
                >
                    <tspan x="58" y="32.02">
                        Imotly
                    </tspan>
                </text>
                <defs>
                    <clipPath id="clip0_8_2">
                        <rect
                            width="24"
                            height="24"
                            fill="white"
                            transform="translate(13 13)"
                        ></rect>
                    </clipPath>
                </defs>
            </svg>
        </div>
    );
};

export default Logo;
